package com.example.studentsback.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置
 * <p>
 * 配置静态资源映射，使上传的头像文件可通过 URL 直接访问。
 * 将 /uploads/** 映射到实际文件存储目录。
 *
 * 例如：访问 http://localhost:8080/uploads/xxx.jpg
 *   实际读取的是 file.upload.path 配置目录下的 xxx.jpg 文件
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}
