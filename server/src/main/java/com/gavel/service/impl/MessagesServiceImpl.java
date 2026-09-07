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


import com.gavel.dao.MessagesDao;
import com.gavel.entity.MessagesEntity;
import com.gavel.service.MessagesService;
import com.gavel.entity.vo.MessagesVO;
import com.gavel.entity.view.MessagesView;

@Service("messagesService")
public class MessagesServiceImpl extends ServiceImpl<MessagesDao, MessagesEntity> implements MessagesService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MessagesEntity> page = this.page(
                new Query<MessagesEntity>(params).getPage(),
                new QueryWrapper<MessagesEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<MessagesEntity> wrapper) {
		  Page<MessagesView> page =new Query<MessagesView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
    @Override
	public List<MessagesVO> selectListVO(Wrapper<MessagesEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public MessagesVO selectVO(Wrapper<MessagesEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<MessagesView> selectListView(Wrapper<MessagesEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public MessagesView selectView(Wrapper<MessagesEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}

}
