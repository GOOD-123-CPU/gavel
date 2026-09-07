package com.gavel.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gavel.utils.PageUtils;
import com.gavel.entity.JingpaidingdanEntity;
import java.util.List;
import java.util.Map;
import com.gavel.entity.vo.JingpaidingdanVO;
import org.apache.ibatis.annotations.Param;
import com.gavel.entity.view.JingpaidingdanView;


/**
 * 竞拍订单
 *
 * @author 
 * @email 
 * @date 2021-02-03 16:07:05
 */
public interface JingpaidingdanService extends IService<JingpaidingdanEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<JingpaidingdanVO> selectListVO(Wrapper<JingpaidingdanEntity> wrapper);
   	
   	JingpaidingdanVO selectVO(@Param("ew") Wrapper<JingpaidingdanEntity> wrapper);
   	
   	List<JingpaidingdanView> selectListView(Wrapper<JingpaidingdanEntity> wrapper);
   	
   	JingpaidingdanView selectView(@Param("ew") Wrapper<JingpaidingdanEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<JingpaidingdanEntity> wrapper);
   	
}

