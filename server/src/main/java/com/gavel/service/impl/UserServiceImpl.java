
package com.gavel.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gavel.dao.UserDao;
import com.gavel.entity.UserEntity;
import com.gavel.service.UserService;
import com.gavel.utils.PageUtils;
import com.gavel.utils.Query;


/**
 * 系统用户
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<UserEntity> page = this.page(
                new Query<UserEntity>(params).getPage(),
                new QueryWrapper<UserEntity>()
        );
        return new PageUtils(page);
	}

	@Override
	public List<UserEntity> selectListView(Wrapper<UserEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params,
			Wrapper<UserEntity> wrapper) {
		 Page<UserEntity> page =new Query<UserEntity>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
	}
}
