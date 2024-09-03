package com.example.echarging.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Charging Pool API")
                        .version("1.0")
                        .description("API documentation for the Charging Pool and Charge Point Status services"));
    }

    @Bean
    public GroupedOpenApi chargingPoolApi() {
        return GroupedOpenApi.builder()
                .group("charging-pool")
                .pathsToMatch("/api/charging-pools/**")
                .build();
    }

    @Bean
    public GroupedOpenApi chargePointStatusApi() {
        return GroupedOpenApi.builder()
                .group("charge-point-status")
                .pathsToMatch("/api/charge-point-status/**")
                .build();
    }
}
