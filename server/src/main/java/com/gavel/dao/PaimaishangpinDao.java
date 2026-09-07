package com.gavel.dao;

import com.gavel.entity.PaimaishangpinEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.apache.ibatis.annotations.Param;
import com.gavel.entity.vo.PaimaishangpinVO;
import com.gavel.entity.view.PaimaishangpinView;


/**
 * 拍卖商品
 * 
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
public interface PaimaishangpinDao extends BaseMapper<PaimaishangpinEntity> {
	
	List<PaimaishangpinVO> selectListVO(@Param("ew") Wrapper<PaimaishangpinEntity> wrapper);
	
	PaimaishangpinVO selectVO(@Param("ew") Wrapper<PaimaishangpinEntity> wrapper);
	
	List<PaimaishangpinView> selectListView(@Param("ew") Wrapper<PaimaishangpinEntity> wrapper);

	List<PaimaishangpinView> selectListView(IPage page, @Param("ew") Wrapper<PaimaishangpinEntity> wrapper);
	
	PaimaishangpinView selectView(@Param("ew") Wrapper<PaimaishangpinEntity> wrapper);
	
}
