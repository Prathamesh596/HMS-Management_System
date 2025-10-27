package com.management.hms.hms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI hmsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HMS API")
                        .description("Hospital Management System APIs")
                        .version("1.0.0")

                        .contact(new Contact()
                                .name("Prathamesh")
                                .email("prathaam19@gmail.com")
                        )
                );
    }
}
