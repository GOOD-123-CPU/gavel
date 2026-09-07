package com.gavel.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gavel.utils.PageUtils;
import com.gavel.entity.ShangpinleixingEntity;
import java.util.List;
import java.util.Map;
import com.gavel.entity.vo.ShangpinleixingVO;
import org.apache.ibatis.annotations.Param;
import com.gavel.entity.view.ShangpinleixingView;


/**
 * 商品类型
 *
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
public interface ShangpinleixingService extends IService<ShangpinleixingEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ShangpinleixingVO> selectListVO(Wrapper<ShangpinleixingEntity> wrapper);
   	
   	ShangpinleixingVO selectVO(@Param("ew") Wrapper<ShangpinleixingEntity> wrapper);
   	
   	List<ShangpinleixingView> selectListView(Wrapper<ShangpinleixingEntity> wrapper);
   	
   	ShangpinleixingView selectView(@Param("ew") Wrapper<ShangpinleixingEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ShangpinleixingEntity> wrapper);
   	
}

