package com.xyu.myapp;
import org.springframework.web.filter.CorsFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5174");  // 允许的前端地址
        config.addAllowedMethod("*");  // 允许所有的请求方法
        config.addAllowedHeader("*");  // 允许所有的请求头
        config.setAllowCredentials(true);  // 允许发送 Cookie

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // 允许所有路径的跨域请求
        return new CorsFilter(source);
    }
}
//中间件
//接受来自前端的所有信息，允许前端的所有信息