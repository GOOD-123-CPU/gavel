package com.gavel.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gavel.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gavel.entity.YonghuEntity;
import com.gavel.entity.view.YonghuView;
import com.gavel.interceptor.AuthorizationInterceptor;
import com.gavel.service.TokenService;
import com.gavel.service.YonghuService;
import com.gavel.utils.MPUtil;
import com.gavel.utils.PageUtils;
import com.gavel.utils.R;

import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 用户
 * 后端接口
 *
 * 安全设计：
 * - 密码（mima）使用 BCrypt 哈希存储，登录时兼容历史明文并自动升级；
 * - resetPass 收回匿名权限（原版任何人可通过用户名重置任意账号密码）；
 * - 会话信息从 request attribute 读取（替代 session）；
 * - 列表/详情接口对 mima、shenfenzheng 做脱敏，防止批量脱库。
 */
@Tag(name = "Members", description = "前台用户")
@RestController
@RequestMapping("/members")
public class YonghuController {

	private static final String DEFAULT_PASSWORD = "123456";

	private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private YonghuService yonghuService;

	@Autowired
	private TokenService tokenService;

	/**
	 * 登录
	 */
	@IgnoreAuth
	@RequestMapping(value = "/login")
	public R login(String username, String password, String captcha, HttpServletRequest request) {
		YonghuEntity user = yonghuService.getOne(new QueryWrapper<YonghuEntity>().eq("yonghuming", username));
		if (user == null || !matchesAndUpgrade(user, password)) {
			return R.error("账号或密码不正确");
		}
		String token = tokenService.generateToken(user.getId(), username, "yonghu", "用户");
		return R.ok().put("token", token);
	}

	/**
	 * 校验密码；历史明文校验通过后自动升级为 BCrypt
	 */
	private boolean matchesAndUpgrade(YonghuEntity user, String rawPassword) {
		if (rawPassword == null) {
			return false;
		}
		String stored = user.getMima();
		boolean ok;
		if (stored != null && stored.startsWith("$2")) {
			ok = ENCODER.matches(rawPassword, stored);
		} else {
			ok = stored != null && stored.equals(rawPassword);
			if (ok) {
				user.setMima(ENCODER.encode(rawPassword));
				yonghuService.updateById(user);
			}
		}
		return ok;
	}

	/**
     * 注册
     */
	@IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody YonghuEntity yonghu) {
    	YonghuEntity user = yonghuService.getOne(new QueryWrapper<YonghuEntity>().eq("yonghuming", yonghu.getYonghuming()));
		if (user != null) {
			return R.error("注册用户已存在");
		}
		if (StringUtils.isBlank(yonghu.getMima())) {
			return R.error("密码不能为空");
		}
		Long uId = new java.util.Date().getTime();
		yonghu.setId(uId);
		yonghu.setMima(ENCODER.encode(yonghu.getMima()));
        yonghuService.save(yonghu);
        return R.ok();
    }

	/**
	 * 退出
	 */
	@RequestMapping("/logout")
	public R logout(HttpServletRequest request) {
		return R.ok("退出成功");
	}

	/**
     * 获取当前登录用户信息（脱敏）
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request) {
    	Long id = (Long) request.getAttribute(AuthorizationInterceptor.ATTR_USER_ID);
        YonghuEntity user = yonghuService.getById(id);
        sanitize(user);
        return R.ok().put("data", user);
    }

    /**
     * 密码重置（需登录后操作，不再匿名）
     */
	@RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request) {
    	YonghuEntity user = yonghuService.getOne(new QueryWrapper<YonghuEntity>().eq("yonghuming", username));
    	if (user == null) {
    		return R.error("账号不存在");
    	}
        user.setMima(ENCODER.encode(DEFAULT_PASSWORD));
        yonghuService.updateById(user);
        return R.ok("密码已重置为：" + DEFAULT_PASSWORD);
    }

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, YonghuEntity yonghu, HttpServletRequest request) {
        QueryWrapper<YonghuEntity> ew = MPUtil.emptyWrapper();
		PageUtils page = yonghuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yonghu), params), params));
        sanitizeList(page.getList());
        return R.ok().put("data", page);
    }

    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, YonghuEntity yonghu, HttpServletRequest request) {
        QueryWrapper<YonghuEntity> ew = MPUtil.emptyWrapper();
		PageUtils page = yonghuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yonghu), params), params));
        sanitizeList(page.getList());
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R lists(YonghuEntity yonghu) {
       	QueryWrapper<YonghuEntity> ew = MPUtil.emptyWrapper();
      	ew.allEq(MPUtil.allEQMapPre(yonghu, "yonghu"));
        return R.ok().put("data", sanitizeList(yonghuService.selectListView(ew)));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(YonghuEntity yonghu) {
        QueryWrapper<YonghuEntity> ew = MPUtil.emptyWrapper();
 		ew.allEq(MPUtil.allEQMapPre(yonghu, "yonghu"));
		YonghuView yonghuView = yonghuService.selectView(ew);
		sanitize(yonghuView);
		return R.ok("查询用户成功").put("data", yonghuView);
    }

    /**
     * 后端详情（脱敏）
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        YonghuEntity yonghu = yonghuService.getById(id);
        sanitize(yonghu);
        return R.ok().put("data", yonghu);
    }

    /**
     * 前端详情（脱敏）
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        YonghuEntity yonghu = yonghuService.getById(id);
        sanitize(yonghu);
        return R.ok().put("data", yonghu);
    }

    /**
     * 后端保存（密码哈希入库）
     */
    @RequestMapping("/save")
    public R save(@RequestBody YonghuEntity yonghu, HttpServletRequest request) {
    	YonghuEntity user = yonghuService.getOne(new QueryWrapper<YonghuEntity>().eq("yonghuming", yonghu.getYonghuming()));
		if (user != null) {
			return R.error("用户已存在");
		}
		if (StringUtils.isNotBlank(yonghu.getMima())) {
			yonghu.setMima(ENCODER.encode(yonghu.getMima()));
		}
		yonghu.setId(new java.util.Date().getTime());
        yonghuService.save(yonghu);
        return R.ok();
    }

    /**
     * 前端保存（密码哈希入库）
     */
    @RequestMapping("/add")
    public R add(@RequestBody YonghuEntity yonghu, HttpServletRequest request) {
    	YonghuEntity user = yonghuService.getOne(new QueryWrapper<YonghuEntity>().eq("yonghuming", yonghu.getYonghuming()));
		if (user != null) {
			return R.error("用户已存在");
		}
		if (StringUtils.isNotBlank(yonghu.getMima())) {
			yonghu.setMima(ENCODER.encode(yonghu.getMima()));
		}
		yonghu.setId(new java.util.Date().getTime());
        yonghuService.save(yonghu);
        return R.ok();
    }

    /**
     * 修改（密码为空则不更新；携带新明文密码则重新哈希）
     */
    @RequestMapping("/update")
    public R update(@RequestBody YonghuEntity yonghu, HttpServletRequest request) {
    	Long loginUserId = (Long) request.getAttribute(AuthorizationInterceptor.ATTR_USER_ID);
    	String role = (String) request.getAttribute(AuthorizationInterceptor.ATTR_TABLE_NAME);
    	// 普通用户只能修改自己的资料
    	if (!"users".equals(role) && !loginUserId.equals(yonghu.getId())) {
    		return R.error("无权修改他人资料");
    	}
    	if (yonghu.getMima() != null && !yonghu.getMima().startsWith("$2")) {
            yonghu.setMima(ENCODER.encode(yonghu.getMima()));
        } else if (yonghu.getMima() == null) {
        	// 防止把密码字段更新为 null
        	yonghu.setMima("");
        	YonghuEntity db = yonghuService.getById(yonghu.getId());
        	if (db != null) {
        		yonghu.setMima(db.getMima());
        	}
        }
        yonghuService.updateById(yonghu);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        yonghuService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 脱敏单条记录：不回传密码与完整身份证号
     */
    private void sanitize(YonghuEntity u) {
    	if (u != null) {
    		u.setMima(null);
    		if (u.getShenfenzheng() != null && u.getShenfenzheng().length() >= 8) {
    			u.setShenfenzheng(u.getShenfenzheng().substring(0, 4) + "***********"
    					+ u.getShenfenzheng().substring(u.getShenfenzheng().length() - 2));
    		}
    	}
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private java.util.List sanitizeList(java.util.List list) {
    	if (list != null) {
    		for (Object o : list) {
    			if (o instanceof YonghuEntity) {
    				sanitize((YonghuEntity) o);
    			}
    		}
    	}
    	return list;
    }
}
