package com.gavel.dao;

import com.gavel.entity.LishijingpaiEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.apache.ibatis.annotations.Param;
import com.gavel.entity.vo.LishijingpaiVO;
import com.gavel.entity.view.LishijingpaiView;


/**
 * 历史竞拍
 * 
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
public interface LishijingpaiDao extends BaseMapper<LishijingpaiEntity> {
	
	List<LishijingpaiVO> selectListVO(@Param("ew") Wrapper<LishijingpaiEntity> wrapper);
	
	LishijingpaiVO selectVO(@Param("ew") Wrapper<LishijingpaiEntity> wrapper);
	
	List<LishijingpaiView> selectListView(@Param("ew") Wrapper<LishijingpaiEntity> wrapper);

	List<LishijingpaiView> selectListView(IPage page, @Param("ew") Wrapper<LishijingpaiEntity> wrapper);
	
	LishijingpaiView selectView(@Param("ew") Wrapper<LishijingpaiEntity> wrapper);
	
}
