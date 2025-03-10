package com.cursos.ec.mssofkatransaction.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Transaction Management API - Sofka")
                        .version("1.3")
                        .description("This API allows managing transaction, including creating, retrieving, updating, and deleting records.")
                        .contact(new Contact()
                                .email("ronnychamba96@gmail.com")
                                .name("Ronny Chamba"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }

}
