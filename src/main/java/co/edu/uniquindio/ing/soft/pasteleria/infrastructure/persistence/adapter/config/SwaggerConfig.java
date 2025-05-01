package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Pastelería")
                        .version("1.0")
                        .description("Documentación de la API de Pastelería")
                        .contact(new Contact().name("Pasteleria Féliz").email("lizmartinez@gmail.com")));
    }
}