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

import com.gavel.entity.ShangpinleixingEntity;
import com.gavel.entity.view.ShangpinleixingView;

import com.gavel.service.ShangpinleixingService;
import com.gavel.service.TokenService;
import com.gavel.utils.PageUtils;
import com.gavel.utils.R;
import com.gavel.utils.MPUtil;
import com.gavel.utils.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * 商品类型
 * 后端接口
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
@Tag(name = "Categories", description = "商品类型")
@RestController
@RequestMapping("/categories")
public class ShangpinleixingController {
    @Autowired
    private ShangpinleixingService shangpinleixingService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ShangpinleixingEntity shangpinleixing, HttpServletRequest request){
        QueryWrapper<ShangpinleixingEntity> ew = new QueryWrapper<ShangpinleixingEntity>();
		PageUtils page = shangpinleixingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shangpinleixing), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ShangpinleixingEntity shangpinleixing, HttpServletRequest request){
        QueryWrapper<ShangpinleixingEntity> ew = new QueryWrapper<ShangpinleixingEntity>();
		PageUtils page = shangpinleixingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shangpinleixing), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ShangpinleixingEntity shangpinleixing){
       	QueryWrapper<ShangpinleixingEntity> ew = new QueryWrapper<ShangpinleixingEntity>();
      	ew.allEq(MPUtil.allEQMapPre( shangpinleixing, "shangpinleixing")); 
        return R.ok().put("data", shangpinleixingService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ShangpinleixingEntity shangpinleixing){
        QueryWrapper< ShangpinleixingEntity> ew = new QueryWrapper< ShangpinleixingEntity>();
 		ew.allEq(MPUtil.allEQMapPre( shangpinleixing, "shangpinleixing")); 
		ShangpinleixingView shangpinleixingView =  shangpinleixingService.selectView(ew);
		return R.ok("查询商品类型成功").put("data", shangpinleixingView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShangpinleixingEntity shangpinleixing = shangpinleixingService.getById(id);
        return R.ok().put("data", shangpinleixing);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ShangpinleixingEntity shangpinleixing = shangpinleixingService.getById(id);
        return R.ok().put("data", shangpinleixing);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShangpinleixingEntity shangpinleixing, HttpServletRequest request){
    	shangpinleixing.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(shangpinleixing);
        shangpinleixingService.save(shangpinleixing);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ShangpinleixingEntity shangpinleixing, HttpServletRequest request){
    	shangpinleixing.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(shangpinleixing);
        shangpinleixingService.save(shangpinleixing);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ShangpinleixingEntity shangpinleixing, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shangpinleixing);
        shangpinleixingService.updateById(shangpinleixing);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        shangpinleixingService.removeByIds(Arrays.asList(ids));
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
		
		QueryWrapper<ShangpinleixingEntity> wrapper = new QueryWrapper<ShangpinleixingEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		long count = shangpinleixingService.count(wrapper);
		return R.ok().put("count", count);
	}
	


}
