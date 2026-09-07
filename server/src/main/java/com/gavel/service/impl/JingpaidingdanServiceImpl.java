package com.gavel.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gavel.utils.PageUtils;
import com.gavel.utils.Query;


import com.gavel.dao.JingpaidingdanDao;
import com.gavel.entity.JingpaidingdanEntity;
import com.gavel.service.JingpaidingdanService;
import com.gavel.entity.vo.JingpaidingdanVO;
import com.gavel.entity.view.JingpaidingdanView;

@Service("jingpaidingdanService")
public class JingpaidingdanServiceImpl extends ServiceImpl<JingpaidingdanDao, JingpaidingdanEntity> implements JingpaidingdanService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<JingpaidingdanEntity> page = this.page(
                new Query<JingpaidingdanEntity>(params).getPage(),
                new QueryWrapper<JingpaidingdanEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<JingpaidingdanEntity> wrapper) {
		  Page<JingpaidingdanView> page =new Query<JingpaidingdanView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
    @Override
	public List<JingpaidingdanVO> selectListVO(Wrapper<JingpaidingdanEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public JingpaidingdanVO selectVO(Wrapper<JingpaidingdanEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<JingpaidingdanView> selectListView(Wrapper<JingpaidingdanEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public JingpaidingdanView selectView(Wrapper<JingpaidingdanEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}

}
