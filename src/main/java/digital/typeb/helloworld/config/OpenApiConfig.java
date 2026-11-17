package digital.typeb.helloworld.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI / Swagger UI configuration for interactive API documentation and testing.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Simple HTTP API")
                        .version("1.0.0")
                        .description("Java Spring Boot REST API exposing the /hello-world endpoint with alphabetical name validation."));
    }
}
