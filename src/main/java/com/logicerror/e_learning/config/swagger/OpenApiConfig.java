package com.logicerror.e_learning.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Learning API")
                        .version("1.0")
                        .description("API documentation for the E-Learning application.")
                        .contact(new Contact()
                                .name("Ahmed Basha")
                                .email("ahmedhassan9124@gmail.com")))
                .addServersItem(new Server().url("/").description("localhost:5000"));
    }

}
