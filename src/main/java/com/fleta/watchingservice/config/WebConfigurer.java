package com.fleta.watchingservice.config;
import com.megazone.framework.config.MegaConstants;
import com.megazone.framework.config.MegaProperties;
import com.megazone.framework.config.h2.H2ConfigurationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
@EnableWebMvc
public class WebConfigurer implements WebMvcConfigurer {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    private final MegaProperties megaProperties;

    public WebConfigurer(Environment env, MegaProperties megaProperties) {
        this.megaProperties = megaProperties;
        if (env.acceptsProfiles(Profiles.of(MegaConstants.SPRING_PROFILE_DEVELOPMENT))) {
            H2ConfigurationHelper.initH2Console();
        }
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = megaProperties.getCors();
        if (!CollectionUtils.isEmpty(config.getAllowedOrigins())) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/management/**", config);
            source.registerCorsConfiguration("/v2/api-docs", config);
            source.registerCorsConfiguration("/v3/api-docs", config);
            source.registerCorsConfiguration("/swagger-resources", config);
            source.registerCorsConfiguration("/swagger-ui/**", config);
        }
        return new CorsFilter(source);
    }
}
