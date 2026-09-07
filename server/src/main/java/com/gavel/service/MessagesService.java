package com.gavel.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gavel.utils.PageUtils;
import com.gavel.entity.MessagesEntity;
import java.util.List;
import java.util.Map;
import com.gavel.entity.vo.MessagesVO;
import org.apache.ibatis.annotations.Param;
import com.gavel.entity.view.MessagesView;


/**
 * 留言板
 *
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
public interface MessagesService extends IService<MessagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<MessagesVO> selectListVO(Wrapper<MessagesEntity> wrapper);
   	
   	MessagesVO selectVO(@Param("ew") Wrapper<MessagesEntity> wrapper);
   	
   	List<MessagesView> selectListView(Wrapper<MessagesEntity> wrapper);
   	
   	MessagesView selectView(@Param("ew") Wrapper<MessagesEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<MessagesEntity> wrapper);
   	
}

