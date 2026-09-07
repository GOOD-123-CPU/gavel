package com.gavel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gavel.interceptor.AuthorizationInterceptor;

/**
 * Web MVC configuration:
 * - Token authentication interceptor
 * - Static resources (admin console / front portal)
 * - CORS whitelist from application.yml (not open by default)
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins:http://localhost:8080}")
    private String allowedOrigins;

    private final AuthorizationInterceptor authInterceptor;

    public InterceptorConfig(AuthorizationInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    /**
     * Public paths that bypass token authentication.
     */
    private static final String[] PUBLIC_PATTERNS = {
            "/static/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/actuator/**"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(PUBLIC_PATTERNS);
    }

    /**
     * Static resource mapping: admin (management console) and front (portal).
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/admin/")
                .addResourceLocations("classpath:/front/")
                .addResourceLocations("classpath:/public/");
    }

    /**
     * CORS: only origins listed in app.cors.allowed-origins are allowed.
     * Configure real domains in production.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(allowedOrigins.split(","))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
