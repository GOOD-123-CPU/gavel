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


import com.gavel.dao.ShangpinleixingDao;
import com.gavel.entity.ShangpinleixingEntity;
import com.gavel.service.ShangpinleixingService;
import com.gavel.entity.vo.ShangpinleixingVO;
import com.gavel.entity.view.ShangpinleixingView;

@Service("shangpinleixingService")
public class ShangpinleixingServiceImpl extends ServiceImpl<ShangpinleixingDao, ShangpinleixingEntity> implements ShangpinleixingService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ShangpinleixingEntity> page = this.page(
                new Query<ShangpinleixingEntity>(params).getPage(),
                new QueryWrapper<ShangpinleixingEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ShangpinleixingEntity> wrapper) {
		  Page<ShangpinleixingView> page =new Query<ShangpinleixingView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
    @Override
	public List<ShangpinleixingVO> selectListVO(Wrapper<ShangpinleixingEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ShangpinleixingVO selectVO(Wrapper<ShangpinleixingEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ShangpinleixingView> selectListView(Wrapper<ShangpinleixingEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ShangpinleixingView selectView(Wrapper<ShangpinleixingEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}

}
