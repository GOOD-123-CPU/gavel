package com.gavel.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gavel.utils.PageUtils;
import com.gavel.entity.NewsEntity;
import java.util.List;
import java.util.Map;
import com.gavel.entity.vo.NewsVO;
import org.apache.ibatis.annotations.Param;
import com.gavel.entity.view.NewsView;


/**
 * 竞拍公告
 *
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
public interface NewsService extends IService<NewsEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<NewsVO> selectListVO(Wrapper<NewsEntity> wrapper);
   	
   	NewsVO selectVO(@Param("ew") Wrapper<NewsEntity> wrapper);
   	
   	List<NewsView> selectListView(Wrapper<NewsEntity> wrapper);
   	
   	NewsView selectView(@Param("ew") Wrapper<NewsEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<NewsEntity> wrapper);
   	
}

