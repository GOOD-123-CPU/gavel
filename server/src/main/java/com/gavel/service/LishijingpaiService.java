package com.gavel.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gavel.utils.PageUtils;
import com.gavel.entity.LishijingpaiEntity;
import java.util.List;
import java.util.Map;
import com.gavel.entity.vo.LishijingpaiVO;
import org.apache.ibatis.annotations.Param;
import com.gavel.entity.view.LishijingpaiView;


/**
 * 历史竞拍
 *
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
public interface LishijingpaiService extends IService<LishijingpaiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<LishijingpaiVO> selectListVO(Wrapper<LishijingpaiEntity> wrapper);
   	
   	LishijingpaiVO selectVO(@Param("ew") Wrapper<LishijingpaiEntity> wrapper);
   	
   	List<LishijingpaiView> selectListView(Wrapper<LishijingpaiEntity> wrapper);
   	
   	LishijingpaiView selectView(@Param("ew") Wrapper<LishijingpaiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<LishijingpaiEntity> wrapper);
   	
}

