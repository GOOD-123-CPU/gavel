package com.gavel.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gavel.utils.PageUtils;
import com.gavel.entity.YonghuEntity;
import java.util.List;
import java.util.Map;
import com.gavel.entity.vo.YonghuVO;
import org.apache.ibatis.annotations.Param;
import com.gavel.entity.view.YonghuView;


/**
 * 用户
 *
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
public interface YonghuService extends IService<YonghuEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<YonghuVO> selectListVO(Wrapper<YonghuEntity> wrapper);
   	
   	YonghuVO selectVO(@Param("ew") Wrapper<YonghuEntity> wrapper);
   	
   	List<YonghuView> selectListView(Wrapper<YonghuEntity> wrapper);
   	
   	YonghuView selectView(@Param("ew") Wrapper<YonghuEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<YonghuEntity> wrapper);
   	
}

