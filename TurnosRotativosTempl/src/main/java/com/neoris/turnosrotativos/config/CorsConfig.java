package com.neoris.turnosrotativos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permite el acceso desde cualquier ruta.
                        .allowedOrigins("*") // Permite cualquier origen.
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos HTTP permitidos.
                        .allowedHeaders("*"); // Cabeceras permitidas.
            }
        };
    }
}