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


import com.gavel.dao.LishijingpaiDao;
import com.gavel.entity.LishijingpaiEntity;
import com.gavel.service.LishijingpaiService;
import com.gavel.entity.vo.LishijingpaiVO;
import com.gavel.entity.view.LishijingpaiView;

@Service("lishijingpaiService")
public class LishijingpaiServiceImpl extends ServiceImpl<LishijingpaiDao, LishijingpaiEntity> implements LishijingpaiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<LishijingpaiEntity> page = this.page(
                new Query<LishijingpaiEntity>(params).getPage(),
                new QueryWrapper<LishijingpaiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<LishijingpaiEntity> wrapper) {
		  Page<LishijingpaiView> page =new Query<LishijingpaiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
    @Override
	public List<LishijingpaiVO> selectListVO(Wrapper<LishijingpaiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public LishijingpaiVO selectVO(Wrapper<LishijingpaiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<LishijingpaiView> selectListView(Wrapper<LishijingpaiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public LishijingpaiView selectView(Wrapper<LishijingpaiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}

}
