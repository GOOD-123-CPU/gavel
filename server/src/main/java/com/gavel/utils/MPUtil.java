package com.gavel.utils;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * Mybatis-Plus工具类（MyBatis-Plus 3.x 适配版）
 */
public class MPUtil {
	public static final char UNDERLINE = '_';

	/**
	 * bean 转下划线键值 Map（供 allEq 使用）。通过反射读取 getter，无第三方依赖。
	 */
	public static Map beanToMap(Object bean) {
		Map<String, Object> map = new java.util.LinkedHashMap<String, Object>();
		if (bean == null) {
			return map;
		}
		for (java.lang.reflect.Method m : bean.getClass().getMethods()) {
			if (!m.getDeclaringClass().equals(Object.class) && m.getParameterCount() == 0
					&& m.getName().startsWith("get") && m.getName().length() > 3) {
				try {
					map.put(Character.toLowerCase(m.getName().charAt(3)) + m.getName().substring(4), m.invoke(bean));
				} catch (Exception ignored) {
				}
			}
		}
		return map;
	}

	/**
	 * bean 转 Map，并跳过 null 值（等价于 hutool 的 beanToMap(bean, false, true)）。
	 */
	public static Map beanToMapIgnoreNull(Object bean) {
		Map<String, Object> full = beanToMap(bean);
		Map<String, Object> result = new java.util.LinkedHashMap<String, Object>();
		for (Map.Entry<String, Object> e : full.entrySet()) {
			if (e.getValue() != null) {
				result.put(e.getKey(), e.getValue());
			}
		}
		return result;
	}

	/**
	 * bean 转下划线键值 Map（供 allEq 使用）
	 */
	public static Map allEQMapPre(Object bean, String pre) {
		Map<String, Object> map = beanToMap(bean);
		return camelToUnderlineMap(map, pre);
	}

	public static Map allEQMap(Object bean) {
		Map<String, Object> map = beanToMap(bean);
		return camelToUnderlineMap(map, "");
	}

	public static QueryWrapper allLikePre(QueryWrapper wrapper, Object bean, String pre) {
		Map<String, Object> map = beanToMap(bean);
		Map result = camelToUnderlineMap(map, pre);
		return genLike(wrapper, result);
	}

	@SuppressWarnings("unchecked")
	public static QueryWrapper allLike(QueryWrapper wrapper, Object bean) {
		Map result = beanToMapIgnoreNull(bean);
		return genLike(wrapper, result);
	}

	@SuppressWarnings("unchecked")
	public static QueryWrapper genLike(QueryWrapper wrapper, Map param) {
		java.util.Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null) {
				continue;
			}
			wrapper.like(key, value.toString());
			i++;
		}
		return wrapper;
	}

	@SuppressWarnings("unchecked")
	public static QueryWrapper likeOrEq(QueryWrapper wrapper, Object bean) {
		Map result = beanToMapIgnoreNull(bean);
		return genLikeOrEq(wrapper, result);
	}

	@SuppressWarnings("unchecked")
	public static QueryWrapper genLikeOrEq(QueryWrapper wrapper, Map param) {
		java.util.Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null) {
				continue;
			}
			if (value.toString().contains("%")) {
				wrapper.like(key, value.toString().replace("%", ""));
			} else {
				wrapper.eq(key, value);
			}
			i++;
		}
		return wrapper;
	}

	@SuppressWarnings("unchecked")
	public static QueryWrapper allEq(QueryWrapper wrapper, Object bean) {
		Map result = beanToMapIgnoreNull(bean);
		return genEq(wrapper, result);
	}

	@SuppressWarnings("unchecked")
	public static QueryWrapper genEq(QueryWrapper wrapper, Map param) {
		java.util.Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null) {
				continue;
			}
			wrapper.eq(key, value);
			i++;
		}
		return wrapper;
	}

	@SuppressWarnings("unchecked")
	public static QueryWrapper between(QueryWrapper wrapper, Map<String, Object> params) {
		for (String key : params.keySet()) {
			String columnName = "";
			if (key.endsWith("_start")) {
				columnName = key.substring(0, key.indexOf("_start"));
				if (params.get(key) != null && StringUtils.isNotBlank(params.get(key).toString())) {
					wrapper.ge(columnName, params.get(key));
				}
			}
			if (key.endsWith("_end")) {
				columnName = key.substring(0, key.indexOf("_end"));
				if (params.get(key) != null && StringUtils.isNotBlank(params.get(key).toString())) {
					wrapper.le(columnName, params.get(key));
				}
			}
		}
		return wrapper;
	}

	/**
	 * 排序（MP3：orderDesc/orderAsc 改为 orderByAsc/orderByDesc）
	 */
	@SuppressWarnings("unchecked")
	public static QueryWrapper sort(QueryWrapper wrapper, Map<String, Object> params) {
		String order = "";
		if (params.get("order") != null && StringUtils.isNotBlank(params.get("order").toString())) {
			order = params.get("order").toString();
		}
		if (params.get("sort") != null && StringUtils.isNotBlank(params.get("sort").toString())) {
			String column = params.get("sort").toString();
			if ("desc".equalsIgnoreCase(order)) {
				wrapper.orderByDesc(Arrays.asList(column));
			} else {
				wrapper.orderByAsc(Arrays.asList(column));
			}
		}
		return wrapper;
	}

	/**
	 * 构造一个可用的空 Wrapper（兼容旧代码 new EntityWrapper<T>() 用法）
	 */
	public static <T> QueryWrapper<T> emptyWrapper() {
		return new QueryWrapper<>();
	}

	/**
	 * 驼峰格式字符串转换为下划线格式字符串
	 */
	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static Map camelToUnderlineMap(Map param, String pre) {
		Map<String, Object> newMap = new java.util.HashMap<String, Object>();
		java.util.Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = camelToUnderline(key);
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		return newMap;
	}
}
