package com.gavel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gavel.annotation.IgnoreAuth;
import com.gavel.entity.EIException;
import com.gavel.service.CommonService;
import com.gavel.utils.R;
import com.gavel.utils.SQLFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 通用接口（联动下拉、按列取值）。
 *
 * 安全设计：
 * 1. 表名/列名必须命中白名单（WHITELIST），杜绝任意表查询；
 * 2. 仅暴露前端实际使用的 option / follow 两个接口，
 *    原版无用的 cal/group/value/remind/sh/matchFace/location 等高危接口已移除。
 */
@Tag(name = "Common", description = "通用联动接口")
@RestController
public class CommonController {

    @Autowired
    private CommonService commonService;

    /**
     * 白名单：表名 -> 允许被下拉/查询的列名集合。
     * 新增业务模块时在此登记，其余表一律拒绝。
     */
    private static final Map<String, Set<String>> WHITELIST = Map.of(
            "paimaishangpin", Set.of("shangpinmingcheng"),
            "shangpinleixing", Set.of("shangpinleixing"),
            "yonghu", Set.of("yonghuming")
    );

    private void check(String tableName, String columnName) {
        SQLFilter.checkIdentifier(tableName);
        SQLFilter.checkIdentifier(columnName);
        Set<String> columns = WHITELIST.get(tableName);
        if (columns == null || !columns.contains(columnName)) {
            throw new EIException("非法的查询目标");
        }
    }

    /**
     * 获取 table 表中的 column 列表(联动下拉接口)
     */
    @IgnoreAuth
    @RequestMapping("/option/{tableName}/{columnName}")
    public R getOption(@PathVariable("tableName") String tableName, @PathVariable("columnName") String columnName) {
        check(tableName, columnName);
        Map<String, Object> params = new HashMap<>();
        params.put("table", tableName);
        params.put("column", columnName);
        List<String> data = commonService.getOption(params);
        return R.ok().put("data", data);
    }

    /**
     * 根据 table 中的 column 获取单条记录（用于联动回填）
     */
    @IgnoreAuth
    @RequestMapping("/follow/{tableName}/{columnName}")
    public R getFollowByOption(@PathVariable("tableName") String tableName,
                               @PathVariable("columnName") String columnName,
                               @RequestParam String columnValue) {
        check(tableName, columnName);
        Map<String, Object> params = new HashMap<>();
        params.put("table", tableName);
        params.put("column", columnName);
        params.put("columnValue", columnValue);
        Map<String, Object> result = commonService.getFollowByOption(params);
        // follow 用于回填竞拍商品/用户信息，隐藏敏感列
        if (result != null) {
            result.remove("mima");
            result.remove("password");
            result.remove("shenfenzheng");
        }
        return R.ok().put("data", result);
    }
}
