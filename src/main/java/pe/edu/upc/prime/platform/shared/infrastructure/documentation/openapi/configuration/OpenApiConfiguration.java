package pe.edu.upc.prime.platform.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI documentation.
 */
@Configuration
public class OpenApiConfiguration {

    /**
     * Configures the OpenAPI documentation for the Prime Fix Platform API.
     *
     * @return the OpenAPI configuration
     */
    @Bean
    public OpenAPI primePlatformOpenApi() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .addServersItem(new Server()
                        .url("https://cadw4v2ry8.us-east-2.awsapprunner.com")
                        .description("Despliegue en AWS App Runner (Producción)"))
                .info(new Info()
                        .title("Prime Fix Platform API")
                        .description("Documentación de la API REST de Prime Fix Platform.")
                        .version("v1.0.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio oficial")
                        .url("https://github.com/prime-fix/backend"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
