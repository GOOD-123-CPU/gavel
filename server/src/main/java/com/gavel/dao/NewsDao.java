package com.gavel.dao;

import com.gavel.entity.NewsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.apache.ibatis.annotations.Param;
import com.gavel.entity.vo.NewsVO;
import com.gavel.entity.view.NewsView;


/**
 * 竞拍公告
 * 
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
public interface NewsDao extends BaseMapper<NewsEntity> {
	
	List<NewsVO> selectListVO(@Param("ew") Wrapper<NewsEntity> wrapper);
	
	NewsVO selectVO(@Param("ew") Wrapper<NewsEntity> wrapper);
	
	List<NewsView> selectListView(@Param("ew") Wrapper<NewsEntity> wrapper);

	List<NewsView> selectListView(IPage page, @Param("ew") Wrapper<NewsEntity> wrapper);
	
	NewsView selectView(@Param("ew") Wrapper<NewsEntity> wrapper);
	
}
