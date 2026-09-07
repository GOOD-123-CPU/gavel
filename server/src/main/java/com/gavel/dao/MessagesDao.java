package com.gavel.dao;

import com.gavel.entity.MessagesEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.apache.ibatis.annotations.Param;
import com.gavel.entity.vo.MessagesVO;
import com.gavel.entity.view.MessagesView;


/**
 * 留言板
 * 
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
public interface MessagesDao extends BaseMapper<MessagesEntity> {
	
	List<MessagesVO> selectListVO(@Param("ew") Wrapper<MessagesEntity> wrapper);
	
	MessagesVO selectVO(@Param("ew") Wrapper<MessagesEntity> wrapper);
	
	List<MessagesView> selectListView(@Param("ew") Wrapper<MessagesEntity> wrapper);

	List<MessagesView> selectListView(IPage page, @Param("ew") Wrapper<MessagesEntity> wrapper);
	
	MessagesView selectView(@Param("ew") Wrapper<MessagesEntity> wrapper);
	
}
