package com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swagger() {
        Info info = new Info().title("Project").description("Project Swagger").version("0.0.1");

        // 세션 쿠키 방식
        String cookieAuth = "Session Cookie";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(cookieAuth);

        Components components = new Components()
                .addSecuritySchemes(cookieAuth, new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.COOKIE)
                        .name("JSESSIONID")  // 세션 쿠키 이름
                );

        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/"))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
