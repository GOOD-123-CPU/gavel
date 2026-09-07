package com.gavel.service;

import java.util.List;
import java.util.Map;

public interface CommonService {
	List<String> getOption(Map<String, Object> params);

	Map<String, Object> getFollowByOption(Map<String, Object> params);
}
