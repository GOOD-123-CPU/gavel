
package com.gavel.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gavel.entity.ConfigEntity;
import com.gavel.utils.PageUtils;


/**
 * 系统用户
 */
public interface ConfigService extends IService<ConfigEntity> {
	PageUtils queryPage(Map<String, Object> params);
}
