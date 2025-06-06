package com.bufalari.building;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.bufalari.building.client")
@OpenAPIDefinition(
        info = @Info(
                title = "Project Management API",
                version = "v1.0",
                description = "API for managing construction projects, their structure, and related data."
        ),
        security = { @SecurityRequirement(name = "bearerAuth") },
        servers = { @Server(url = "/", description = "Default Server URL") }
)
@SecuritySchemes({
        @SecurityScheme(
                name = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT",
                in = SecuritySchemeIn.HEADER,
                description = "JWT Authorization header using the Bearer scheme. Example: 'Authorization: Bearer {token}'"
        )
})
public class ProjectManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementServiceApplication.class, args);
	}

}