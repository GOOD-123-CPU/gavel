
package com.gavel.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 查询参数（MyBatis-Plus 3.x 适配版）
 */
public class Query<T> extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
    /**
     * mybatis-plus分页参数
     */
    private Page<T> page;
    /**
     * 当前页码
     */
    private int currPage = 1;
    /**
     * 每页条数
     */
    private int limit = 10;

    public Query(Map<String, Object> params){
        this.putAll(params);

        //分页参数
        if(params.get("page") != null){
            currPage = Integer.parseInt(params.get("page").toString());
        }
        if(params.get("limit") != null){
            limit = Integer.parseInt(params.get("limit").toString());
        }
        // 防止恶意大分页拖垮数据库
        if (limit > 100) {
            limit = 100;
        }

        this.put("offset", (currPage - 1) * limit);
        this.put("page", currPage);
        this.put("limit", limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = SQLFilter.sqlInject((String)params.get("sidx"));
        String order = SQLFilter.sqlInject((String)params.get("order"));
        this.put("sidx", sidx);
        this.put("order", order);

        //mybatis-plus分页（MP3：排序通过 addOrder 指定，列名已经过 SQLFilter 过滤）
        this.page = new Page<>(currPage, limit);

        //排序
        if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)){
            if("ASC".equalsIgnoreCase(order)){
                this.page.addOrder(com.baomidou.mybatisplus.core.metadata.OrderItem.asc(sidx));
            } else {
                this.page.addOrder(com.baomidou.mybatisplus.core.metadata.OrderItem.desc(sidx));
            }
        }
    }

    public Page<T> getPage() {
        return page;
    }

    public int getCurrPage() {
        return currPage;
    }

    public int getLimit() {
        return limit;
    }
}
