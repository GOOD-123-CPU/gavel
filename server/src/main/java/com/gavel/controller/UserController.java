
package com.gavel.controller;


import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gavel.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gavel.entity.UserEntity;
import com.gavel.interceptor.AuthorizationInterceptor;
import com.gavel.service.TokenService;
import com.gavel.service.UserService;
import com.gavel.utils.MPUtil;
import com.gavel.utils.PageUtils;
import com.gavel.utils.R;

import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 管理员登录相关
 *
 * 安全设计：
 * - 密码使用 BCrypt 哈希存储与校验（兼容历史明文：首次登录自动升级为哈希）；
 * - resetPass 不再开放匿名调用（原版任何人可把管理员密码重置为 123456）；
 * - 返回给前端的用户数据不包含密码字段。
 */
@RequestMapping("users")
@Tag(name = "Admin Auth", description = "管理员认证")
@RestController
public class UserController {

	/** 默认初始密码（仅用于重置，需登录后由管理员操作） */
	private static final String DEFAULT_PASSWORD = "123456";

	private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

	@Autowired
	private UserService userService;

	@Autowired
	private TokenService tokenService;

	/**
	 * 登录
	 */
	@IgnoreAuth
	@PostMapping(value = "/login")
	public R login(String username, String password, HttpServletRequest request) {
		UserEntity user = userService.getOne(new QueryWrapper<UserEntity>().eq("username", username));
		if (user == null || !matchesAndUpgrade(user, password)) {
			return R.error("账号或密码不正确");
		}
		String token = tokenService.generateToken(user.getId(), username, "users", user.getRole());
		return R.ok().put("token", token);
	}

	/**
	 * 校验密码；若库里仍是历史明文则校验通过后自动升级为 BCrypt 哈希
	 */
	private boolean matchesAndUpgrade(UserEntity user, String rawPassword) {
		if (rawPassword == null) {
			return false;
		}
		String stored = user.getPassword();
		boolean ok;
		if (stored != null && stored.startsWith("$2")) {
			ok = ENCODER.matches(rawPassword, stored);
		} else {
			// 兼容历史明文数据
			ok = stored != null && stored.equals(rawPassword);
			if (ok) {
				user.setPassword(ENCODER.encode(rawPassword));
				userService.updateById(user);
			}
		}
		return ok;
	}

	/**
	 * 注册（保持原有行为，密码哈希入库）
	 */
	@IgnoreAuth
	@PostMapping(value = "/register")
	public R register(@RequestBody UserEntity user) {
		if (userService.getOne(new QueryWrapper<UserEntity>().eq("username", user.getUsername())) != null) {
			return R.error("用户已存在");
		}
		user.setPassword(ENCODER.encode(user.getPassword()));
		user.setRole("管理员");
		userService.save(user);
		return R.ok();
	}

	/**
	 * 退出
	 */
	@GetMapping(value = "logout")
	public R logout(HttpServletRequest request) {
		return R.ok("退出成功");
	}

	/**
	 * 密码重置（需管理员登录后调用；原版为匿名接口，属于严重漏洞）
	 */
	@RequestMapping(value = "/resetPass")
	public R resetPass(String username, HttpServletRequest request) {
		UserEntity user = userService.getOne(new QueryWrapper<UserEntity>().eq("username", username));
		if (user == null) {
			return R.error("账号不存在");
		}
		user.setPassword(ENCODER.encode(DEFAULT_PASSWORD));
		userService.updateById(user);
		return R.ok("密码已重置为：" + DEFAULT_PASSWORD);
	}

	/**
     * 列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, UserEntity user) {
        QueryWrapper<UserEntity> ew = MPUtil.emptyWrapper();
    	PageUtils page = userService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.allLike(ew, user), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/list")
    public R list(UserEntity user) {
       	QueryWrapper<UserEntity> ew = MPUtil.emptyWrapper();
      	ew.allEq(MPUtil.allEQMapPre(user, "user"));
        return R.ok().put("data", userService.selectListView(ew));
    }

    /**
     * 信息（隐藏密码）
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id) {
        UserEntity user = userService.getById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return R.ok().put("data", user);
    }

    /**
     * 获取当前登录用户信息（隐藏密码）
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request) {
    	Long id = (Long) request.getAttribute(AuthorizationInterceptor.ATTR_USER_ID);
        UserEntity user = userService.getById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return R.ok().put("data", user);
    }

    /**
     * 保存（密码哈希入库）
     */
    @PostMapping("/save")
    public R save(@RequestBody UserEntity user) {
    	if (userService.getOne(new QueryWrapper<UserEntity>().eq("username", user.getUsername())) != null) {
    		return R.error("用户已存在");
    	}
    	user.setPassword(ENCODER.encode(user.getPassword()));
        userService.save(user);
        return R.ok();
    }

    /**
     * 修改（若携带新密码则重新哈希）
     */
    @RequestMapping("/update")
    public R update(@RequestBody UserEntity user) {
        if (user.getPassword() != null && !user.getPassword().startsWith("$2")) {
            user.setPassword(ENCODER.encode(user.getPassword()));
        }
        userService.updateById(user);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        userService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
