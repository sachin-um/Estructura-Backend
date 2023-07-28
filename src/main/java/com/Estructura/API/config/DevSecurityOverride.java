package com.Estructura.API.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;


// Configure the app to accept requests from localhost:3000
@Configuration
@EnableWebMvc
public class DevSecurityOverride implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path fileUploadDir= Paths.get("./files");
        String fileUploadPath=fileUploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/files/**").addResourceLocations("file:/"+fileUploadPath+"/");
    }
}
