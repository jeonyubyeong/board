package com.example.board.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String resourcePath = "/upload/**";

    private final String savePath = "file:///E:/Users/LG/Desktop/board/src/main/resources/upload_files/";
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }
}
