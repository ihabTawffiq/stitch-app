package io.stitch.stitch.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(false);
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("https://stitch-app-production.up.railway.app");
        config.addAllowedOrigin("http://stitch-app-production.up.railway.app");
        config.addAllowedOrigin("https://stitch-app.railway.internal");
        config.addAllowedOrigin("http://stitch-app.railway.internal");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
