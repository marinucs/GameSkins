package com.itacademy.gameskins.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Documentación de la API")
                        .version("1.0.0")
                        .description("Esta es la documentación de la API utilizando OpenAPI y Swagger con SpringDoc."));
    }
}
