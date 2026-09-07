
package com.gavel.controller;


import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gavel.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gavel.entity.ConfigEntity;
import com.gavel.service.ConfigService;
import com.gavel.utils.PageUtils;
import com.gavel.utils.R;
import com.gavel.utils.ValidatorUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 登录相关
 */
@RequestMapping("config")
@Tag(name = "System Config", description = "轮播图/系统配置")
@RestController
public class ConfigController{
	
	@Autowired
	private ConfigService configService;

	/**
     * 列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ConfigEntity config){
        QueryWrapper<ConfigEntity> ew = new QueryWrapper<ConfigEntity>();
    	PageUtils page = configService.queryPage(params);
        return R.ok().put("data", page);
    }
    
	/**
     * 列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ConfigEntity config){
        QueryWrapper<ConfigEntity> ew = new QueryWrapper<ConfigEntity>();
    	PageUtils page = configService.queryPage(params);
        return R.ok().put("data", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        ConfigEntity config = configService.getById(id);
        return R.ok().put("data", config);
    }
    
    /**
     * 详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") String id){
        ConfigEntity config = configService.getById(id);
        return R.ok().put("data", config);
    }
    
    /**
     * 根据name获取信息
     */
    @RequestMapping("/info")
    public R infoByName(@RequestParam String name){
        ConfigEntity config = configService.getOne(new QueryWrapper<ConfigEntity>().eq("name", "faceFile"));
        return R.ok().put("data", config);
    }
    
    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ConfigEntity config){
//    	ValidatorUtils.validateEntity(config);
    	configService.save(config);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ConfigEntity config){
//        ValidatorUtils.validateEntity(config);
        configService.updateById(config);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
    	configService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
