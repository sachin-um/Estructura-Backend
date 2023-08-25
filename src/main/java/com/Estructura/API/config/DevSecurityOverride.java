package com.Estructura.API.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;


// Configure the app to accept requests from localhost:3000
@Configuration
@EnableWebMvc
public class DevSecurityOverride implements WebMvcConfigurer {
    private static final Long MAX_AGE = 36L;
    private static final int CORS_FILTER_ORDER = -102;

    @NotNull
    private static CorsConfiguration getCorsConfiguration() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://127.0.0.1:3001");
        config.addAllowedOrigin("http://127.0.0.1:3000");
        config.setAllowedHeaders(
            Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE,
                          HttpHeaders.ACCEPT
            ));
        config.setAllowedMethods(
            Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(),
                          HttpMethod.PUT.name(), HttpMethod.DELETE.name()
            ));
        config.setMaxAge(MAX_AGE);
        return config;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = getCorsConfiguration();
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
            new CorsFilter(source));

        // should be set order to -100 because we need to CorsFilter before
        // SpringSecurityFilter
        bean.setOrder(CORS_FILTER_ORDER);
        return bean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path   fileUploadDir  = Paths.get("./files");
        String fileUploadPath = fileUploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:/" + fileUploadPath + "/");
    }
}
