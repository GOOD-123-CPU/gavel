package com.gavel.utils;

import com.gavel.entity.EIException;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL过滤
 */
public class SQLFilter {

    /**
     * SQL注入过滤（用于排序字段等需要拼接的场景）
     * @param str  待验证的字符串
     */
    public static String sqlInject(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        //只允许字母、数字、下划线，其他一律拒绝
        if(!str.matches("^[A-Za-z0-9_]+$")){
            throw new EIException("包含非法字符");
        }
        //非法关键字
        String lower = str.toLowerCase();
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update",
                "declare", "alter", "drop", "sleep", "benchmark", "union", "exec", "information_schema"};
        for(String keyword : keywords){
            if(lower.contains(keyword)){
                throw new EIException("包含非法字符");
            }
        }
        return str;
    }

    /**
     * 校验动态表名/列名是否在白名单内（配合 CommonController 使用）
     */
    public static void checkWhitelisted(String table, String column, java.util.Set<String> whitelist) {
        if (table == null || !whitelist.contains(table)) {
            throw new EIException("非法的表名");
        }
        if (column == null || !whitelist.contains(table + "." + column)) {
            throw new EIException("非法的列名");
        }
    }

    /**
     * 检查标识符合法性（表名/列名通用）
     */
    public static String checkIdentifier(String name) {
        if (name == null || !name.matches("^[A-Za-z0-9_]+$")) {
            throw new EIException("非法的标识符");
        }
        return name;
    }
}
