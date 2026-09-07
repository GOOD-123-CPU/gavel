package com.gavel.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

import com.gavel.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.gavel.annotation.IgnoreAuth;

import com.gavel.entity.LishijingpaiEntity;
import com.gavel.entity.view.LishijingpaiView;

import com.gavel.service.LishijingpaiService;
import com.gavel.service.TokenService;
import com.gavel.utils.PageUtils;
import com.gavel.utils.R;
import com.gavel.utils.MPUtil;
import com.gavel.utils.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * 历史竞拍
 * 后端接口
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
@Tag(name = "Bid History", description = "历史竞拍")
@RestController
@RequestMapping("/bid-history")
public class LishijingpaiController {
    @Autowired
    private LishijingpaiService lishijingpaiService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,LishijingpaiEntity lishijingpai, HttpServletRequest request){
		String tableName = (String) request.getAttribute(com.gavel.interceptor.AuthorizationInterceptor.ATTR_TABLE_NAME);
		if(tableName.equals("yonghu")) {
			lishijingpai.setYonghuming((String)request.getAttribute(com.gavel.interceptor.AuthorizationInterceptor.ATTR_USERNAME));
		}
        QueryWrapper<LishijingpaiEntity> ew = new QueryWrapper<LishijingpaiEntity>();
		PageUtils page = lishijingpaiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, lishijingpai), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,LishijingpaiEntity lishijingpai, HttpServletRequest request){
        QueryWrapper<LishijingpaiEntity> ew = new QueryWrapper<LishijingpaiEntity>();
		PageUtils page = lishijingpaiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, lishijingpai), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( LishijingpaiEntity lishijingpai){
       	QueryWrapper<LishijingpaiEntity> ew = new QueryWrapper<LishijingpaiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( lishijingpai, "lishijingpai")); 
        return R.ok().put("data", lishijingpaiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(LishijingpaiEntity lishijingpai){
        QueryWrapper< LishijingpaiEntity> ew = new QueryWrapper< LishijingpaiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( lishijingpai, "lishijingpai")); 
		LishijingpaiView lishijingpaiView =  lishijingpaiService.selectView(ew);
		return R.ok("查询历史竞拍成功").put("data", lishijingpaiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        LishijingpaiEntity lishijingpai = lishijingpaiService.getById(id);
        return R.ok().put("data", lishijingpai);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        LishijingpaiEntity lishijingpai = lishijingpaiService.getById(id);
        return R.ok().put("data", lishijingpai);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody LishijingpaiEntity lishijingpai, HttpServletRequest request){
    	lishijingpai.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(lishijingpai);
        lishijingpaiService.save(lishijingpai);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody LishijingpaiEntity lishijingpai, HttpServletRequest request){
    	lishijingpai.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(lishijingpai);
        lishijingpaiService.save(lishijingpai);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody LishijingpaiEntity lishijingpai, HttpServletRequest request){
        //ValidatorUtils.validateEntity(lishijingpai);
        lishijingpaiService.updateById(lishijingpai);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        lishijingpaiService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		QueryWrapper<LishijingpaiEntity> wrapper = new QueryWrapper<LishijingpaiEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = (String) request.getAttribute(com.gavel.interceptor.AuthorizationInterceptor.ATTR_TABLE_NAME);
		if(tableName.equals("yonghu")) {
			wrapper.eq("yonghuming", (String)request.getAttribute(com.gavel.interceptor.AuthorizationInterceptor.ATTR_USERNAME));
		}

		long count = lishijingpaiService.count(wrapper);
		return R.ok().put("count", count);
	}
	


}
