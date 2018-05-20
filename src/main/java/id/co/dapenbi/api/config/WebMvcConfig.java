package id.co.dapenbi.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("PUT", "DELETE", "POST", "GET", "OPTIONS")
                .allowedHeaders("token", "Content-Type", "Accept", "X-Requested-With")
                .exposedHeaders("token", "Access-Control-Allow-Origin")
                .allowCredentials(true)
                .maxAge(3600);
    }


}