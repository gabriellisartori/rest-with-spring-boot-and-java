package io.github.gabriellisartori.class11_above.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST API Documentation: Spring Boot 2026 REST API's do 0 à AWS e GCP c Java e Docker")
                        .version("v1")
                        .description("This is a sample Spring Boot RESTful service using OpenAPI 3.")
                );
    }
}
