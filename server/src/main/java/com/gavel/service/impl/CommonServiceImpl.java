
package com.gavel.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gavel.dao.CommonDao;
import com.gavel.service.CommonService;


/**
 * 通用查询服务
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonDao commonDao;

	@Override
	public List<String> getOption(Map<String, Object> params) {
		return commonDao.getOption(params);
	}

	@Override
	public Map<String, Object> getFollowByOption(Map<String, Object> params) {
		return commonDao.getFollowByOption(params);
	}

}
