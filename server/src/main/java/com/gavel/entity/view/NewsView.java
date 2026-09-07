package com.gavel.entity.view;

import com.gavel.entity.NewsEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
 

/**
 * 竞拍公告
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
@TableName("news")
public class NewsView  extends NewsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public NewsView(){
	}
 
 	public NewsView(NewsEntity newsEntity){
 	try {
			BeanUtils.copyProperties(newsEntity, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
 		
	}
}
