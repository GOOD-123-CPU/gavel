package com.gavel.entity.view;

import com.gavel.entity.PaimaishangpinEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
 

/**
 * 拍卖商品
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
@TableName("paimaishangpin")
public class PaimaishangpinView  extends PaimaishangpinEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public PaimaishangpinView(){
	}
 
 	public PaimaishangpinView(PaimaishangpinEntity paimaishangpinEntity){
 	try {
			BeanUtils.copyProperties(paimaishangpinEntity, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
 		
	}
}
