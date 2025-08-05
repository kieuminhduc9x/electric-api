package com.example.electric_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Power Grid Management System API",
        description = "Comprehensive REST API for Power Grid Management System with real-time monitoring, device management, alert system, and user management.",
        version = "v1.0.0",
        contact = @Contact(
            name = "Power Grid API Support",
            email = "support@powergrid.com",
            url = "https://powergrid.com/support"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(
            description = "Local Development Server",
            url = "http://localhost:3000"
        ),
        @Server(
            description = "Alternative Local Server",
            url = "http://127.0.0.1:3000"
        )
    }
)
@SecurityScheme(
    name = "Bearer Authentication",
    description = "JWT Bearer token authentication. Login via /api/auth/login to get your token.",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
    // Configuration is handled by annotations above
} 