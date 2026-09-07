package com.gavel.dao;

import com.gavel.entity.YonghuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.apache.ibatis.annotations.Param;
import com.gavel.entity.vo.YonghuVO;
import com.gavel.entity.view.YonghuView;


/**
 * 用户
 * 
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
public interface YonghuDao extends BaseMapper<YonghuEntity> {
	
	List<YonghuVO> selectListVO(@Param("ew") Wrapper<YonghuEntity> wrapper);
	
	YonghuVO selectVO(@Param("ew") Wrapper<YonghuEntity> wrapper);
	
	List<YonghuView> selectListView(@Param("ew") Wrapper<YonghuEntity> wrapper);

	List<YonghuView> selectListView(IPage page, @Param("ew") Wrapper<YonghuEntity> wrapper);
	
	YonghuView selectView(@Param("ew") Wrapper<YonghuEntity> wrapper);
	
}
