package com.gavel.dao;

import com.gavel.entity.JingpaidingdanEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.apache.ibatis.annotations.Param;
import com.gavel.entity.vo.JingpaidingdanVO;
import com.gavel.entity.view.JingpaidingdanView;


/**
 * 竞拍订单
 * 
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
public interface JingpaidingdanDao extends BaseMapper<JingpaidingdanEntity> {
	
	List<JingpaidingdanVO> selectListVO(@Param("ew") Wrapper<JingpaidingdanEntity> wrapper);
	
	JingpaidingdanVO selectVO(@Param("ew") Wrapper<JingpaidingdanEntity> wrapper);
	
	List<JingpaidingdanView> selectListView(@Param("ew") Wrapper<JingpaidingdanEntity> wrapper);

	List<JingpaidingdanView> selectListView(IPage page, @Param("ew") Wrapper<JingpaidingdanEntity> wrapper);
	
	JingpaidingdanView selectView(@Param("ew") Wrapper<JingpaidingdanEntity> wrapper);
	
}
