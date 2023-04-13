package com.api.hotelsearch.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        
        return new OpenAPI()
                .info(new Info().title("Hotel Search API").description(
                        "API to do hotel availabilities searches.")
                        .version("v0.0.1"));
    }

}
