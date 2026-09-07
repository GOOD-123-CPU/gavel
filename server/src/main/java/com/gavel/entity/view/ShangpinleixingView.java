package com.gavel.entity.view;

import com.gavel.entity.ShangpinleixingEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
 

/**
 * 商品类型
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
@TableName("shangpinleixing")
public class ShangpinleixingView  extends ShangpinleixingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public ShangpinleixingView(){
	}
 
 	public ShangpinleixingView(ShangpinleixingEntity shangpinleixingEntity){
 	try {
			BeanUtils.copyProperties(shangpinleixingEntity, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
 		
	}
}
