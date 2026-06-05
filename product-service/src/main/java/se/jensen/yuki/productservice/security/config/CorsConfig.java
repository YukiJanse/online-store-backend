package se.jensen.yuki.productservice.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuration class to set up CORS (Cross-Origin Resource Sharing) settings.
 */
@Slf4j
@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        List<String> allowedOrigins = List.of(
                "http://localhost:5173",
                "http://94.255.243.225",
                "https://yukisonlinestore.netlify.app"
        );

        // Allow all origins
        config.setAllowedOrigins(allowedOrigins); // Note: In production, specify allowed origins for better security
        // Allow specific HTTP methods
        log.debug("Cors allowed origins: {}", allowedOrigins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Allow specific headers
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        // Expose specific headers to the client
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);
        // Apply the CORS configuration to all paths
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
