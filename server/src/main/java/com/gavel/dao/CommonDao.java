
package com.gavel.dao;

import java.util.List;
import java.util.Map;

/**
 * 通用接口（仅保留白名单内的联动查询）
 */
public interface CommonDao{
	List<String> getOption(Map<String, Object> params);

	Map<String, Object> getFollowByOption(Map<String, Object> params);
}
