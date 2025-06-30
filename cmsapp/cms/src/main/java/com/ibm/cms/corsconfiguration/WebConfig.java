package com.ibm.cms.corsconfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5500") // ✅ Allow both localhost & 127.0.0.1
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")       // ✅ OPTIONS important for pre-flight
                .allowedHeaders("*")                                             // ✅ Allow all headers
                .allowCredentials(true);                                         // ✅ Allow cookies/credentials
    }
}
