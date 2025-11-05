package com.franchise.challenge.infraestructure.entrypoint.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Franchise Challenge API",
                version = "v1",
                description = "API reactiva para gestión de franquicias, sucursales y productos."
        )
)
public class OpenApiConfig { }
