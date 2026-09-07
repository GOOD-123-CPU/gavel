
package com.gavel.service.impl;


import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gavel.dao.ConfigDao;
import com.gavel.entity.ConfigEntity;
import com.gavel.entity.UserEntity;
import com.gavel.service.ConfigService;
import com.gavel.utils.PageUtils;
import com.gavel.utils.Query;


/**
 * 系统用户
 */
@Service("configService")
public class ConfigServiceImpl extends ServiceImpl<ConfigDao, ConfigEntity> implements ConfigService {
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<ConfigEntity> page = this.page(
                new Query<ConfigEntity>(params).getPage(),
                new QueryWrapper<ConfigEntity>()
        );
        return new PageUtils(page);
	}
}
