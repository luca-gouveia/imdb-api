package com.gouveia.imdb;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "IMDB api OpenApi", version = "1", description = "API desenvolvida para fornecer recursos para o IMDB"))
public class ImdbApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImdbApplication.class, args);
    }
}
