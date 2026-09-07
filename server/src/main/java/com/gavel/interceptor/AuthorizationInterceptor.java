package com.gavel.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.gavel.annotation.IgnoreAuth;
import com.gavel.entity.TokenEntity;
import com.gavel.service.TokenService;
import com.gavel.utils.R;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证。
 * CORS 已统一交由 InterceptorConfig 处理，此处只做认证。
 * 登录信息写入 request attribute（替代 session，服务端无状态）。
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    public static final String LOGIN_TOKEN_KEY = "Token";
    public static final String ATTR_USER_ID = "userId";
    public static final String ATTR_ROLE = "role";
    public static final String ATTR_TABLE_NAME = "tableName";
    public static final String ATTR_USERNAME = "username";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final TokenService tokenService;

    public AuthorizationInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 跨域预检请求直接放行（CORS 由全局配置处理）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpStatus.OK.value());
            return false;
        }

        // 非控制器方法（如静态资源）直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        IgnoreAuth annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        // 不需要验证权限的方法直接放过
        if (annotation != null) {
            return true;
        }

        // 从 header 中获取 token
        String token = request.getHeader(LOGIN_TOKEN_KEY);

        TokenEntity tokenEntity = null;
        if (StringUtils.isNotBlank(token)) {
            tokenEntity = tokenService.getTokenEntity(token);
        }

        if (tokenEntity != null) {
            request.setAttribute(ATTR_USER_ID, tokenEntity.getUserid());
            request.setAttribute(ATTR_ROLE, tokenEntity.getRole());
            request.setAttribute(ATTR_TABLE_NAME, tokenEntity.getTablename());
            request.setAttribute(ATTR_USERNAME, tokenEntity.getUsername());
            return true;
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        try (PrintWriter writer = response.getWriter()) {
            writer.write(MAPPER.writeValueAsString(R.error(401, "请先登录")));
        } catch (IOException ignored) {
        }
        return false;
    }
}
