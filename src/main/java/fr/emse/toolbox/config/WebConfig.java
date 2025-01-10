package fr.emse.toolbox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // Mark this class as a configuration class
public class WebConfig implements WebMvcConfigurer {

    // This method configures global CORS settings
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Define which endpoints can be accessed by the frontend
        registry.addMapping("/api/**")  // Allow access to all /api/** endpoints
                .allowedOrigins("http://localhost:3000")  // Allow requests from the frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow specific HTTP methods
                .allowedHeaders("*")  // Allow all headers in the requests
                .allowCredentials(true);  // Allow sending cookies and authentication headers
    }
}
