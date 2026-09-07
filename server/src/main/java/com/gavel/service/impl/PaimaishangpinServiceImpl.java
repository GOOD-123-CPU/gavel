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


import com.gavel.dao.PaimaishangpinDao;
import com.gavel.entity.PaimaishangpinEntity;
import com.gavel.service.PaimaishangpinService;
import com.gavel.entity.vo.PaimaishangpinVO;
import com.gavel.entity.view.PaimaishangpinView;

@Service("paimaishangpinService")
public class PaimaishangpinServiceImpl extends ServiceImpl<PaimaishangpinDao, PaimaishangpinEntity> implements PaimaishangpinService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PaimaishangpinEntity> page = this.page(
                new Query<PaimaishangpinEntity>(params).getPage(),
                new QueryWrapper<PaimaishangpinEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<PaimaishangpinEntity> wrapper) {
		  Page<PaimaishangpinView> page =new Query<PaimaishangpinView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
    @Override
	public List<PaimaishangpinVO> selectListVO(Wrapper<PaimaishangpinEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public PaimaishangpinVO selectVO(Wrapper<PaimaishangpinEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<PaimaishangpinView> selectListView(Wrapper<PaimaishangpinEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public PaimaishangpinView selectView(Wrapper<PaimaishangpinEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}

}
