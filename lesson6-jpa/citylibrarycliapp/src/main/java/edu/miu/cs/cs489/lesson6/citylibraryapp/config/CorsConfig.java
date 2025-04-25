package edu.miu.cs.cs489.lesson6.citylibraryapp.config;

//import org.apache.catalina.filters.CorsFilter;
import org.springframework.web.filter.CorsFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 1. Create configuration source
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 2. Create configuration
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");  // Use allowedOriginPatterns instead of allowedOrigins
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        // 3. Register configuration for all paths
        source.registerCorsConfiguration("/**", config);

        // 4. Create the filter with the source
        return new CorsFilter(source);
    }
}
