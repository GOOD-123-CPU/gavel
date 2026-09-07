package com.gavel.dao;

import com.gavel.entity.ShangpinleixingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.apache.ibatis.annotations.Param;
import com.gavel.entity.vo.ShangpinleixingVO;
import com.gavel.entity.view.ShangpinleixingView;


/**
 * 商品类型
 * 
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
public interface ShangpinleixingDao extends BaseMapper<ShangpinleixingEntity> {
	
	List<ShangpinleixingVO> selectListVO(@Param("ew") Wrapper<ShangpinleixingEntity> wrapper);
	
	ShangpinleixingVO selectVO(@Param("ew") Wrapper<ShangpinleixingEntity> wrapper);
	
	List<ShangpinleixingView> selectListView(@Param("ew") Wrapper<ShangpinleixingEntity> wrapper);

	List<ShangpinleixingView> selectListView(IPage page, @Param("ew") Wrapper<ShangpinleixingEntity> wrapper);
	
	ShangpinleixingView selectView(@Param("ew") Wrapper<ShangpinleixingEntity> wrapper);
	
}
