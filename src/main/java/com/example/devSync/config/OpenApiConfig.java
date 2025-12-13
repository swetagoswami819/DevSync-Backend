package com.example.devSync.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "DevSync - Project Collaboration & Task Management API",
                description = "DevSync is a team-based project collaboration platform that allows users to create projects, manage tasks, assign members, track task status, update progress, and collaborate efficiently in real time.",
                version = "1.0",
                contact = @Contact(
                        name = "Sweta Goswami",
                        email = "sweta.goswami@example.com"
                )
        ),
        servers = {
                @Server(
                        description = "Local Development Server",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
}
