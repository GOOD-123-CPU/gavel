package com.gavel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI 3 documentation for Gavel.
 * Swagger UI: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

	private static final String SECURITY_SCHEME_NAME = "TokenAuth";

	@Bean
	public OpenAPI gavelOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title("Gavel API")
						.description("Gavel — Open-source Online Auction Platform. "
								+ "All business endpoints (except auth) require the `Token` header. "
								+ "Get a token via `POST /users/login` or `POST /members/login`.")
						.version("v2.0.0")
						.contact(new Contact().name("Gavel Project").url("https://github.com/your-org/gavel"))
						.license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
				.components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME,
						new SecurityScheme()
								.type(SecurityScheme.Type.APIKEY)
								.in(SecurityScheme.In.HEADER)
								.name("Token")
								.description("Session token returned by the login endpoints")))
				.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
	}
}
