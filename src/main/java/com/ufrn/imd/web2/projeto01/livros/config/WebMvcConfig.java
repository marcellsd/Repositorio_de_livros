package com.ufrn.imd.web2.projeto01.livros.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://10.0.2.2:8080", "http://10.0.2.2:8081").allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS", "PUT").allowCredentials(true);
        
    }
    
}