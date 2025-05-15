package pe.edu.upc.managewise.backend.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
//*************************iam*************************
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//*************************iam*************************

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI managewiseBackendOpenApi() {
        // General configuration
        var openApi = new OpenAPI();
        openApi
                .info(new Info()
                        .title("Managewise Backend API")
                        .description("Managewise backend REST API documentation.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0")
                                .url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Managewise Backend Documentation")
                        .url("https://github.com/Horizon-ManageWise/managewise-backend/tree/develop"));
//*************************iam*************************(se tiene que quitar los comentarios cuando añadamos el IAM)
        // Add security scheme
//        final String securitySchemeName = "bearerAuth";
//
//        openApi.addSecurityItem(new SecurityRequirement()
//                        .addList(securitySchemeName))
//                .components(new Components()
//                        .addSecuritySchemes(securitySchemeName,
//                                new SecurityScheme()
//                                        .name(securitySchemeName)
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")));

        // Return OpenAPI configuration object with all the settings
//*************************iam*************************
        return openApi;
    }
}