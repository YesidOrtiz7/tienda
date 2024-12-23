package com.tienda.webConfigSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();

        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200","http://localhost:5173"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));//admitira todos los encabezados
        corsConfiguration.addExposedHeader("Authorization");

        /*con esta instruccion estamos aplicando a todos los controladores la configuracion corsConfiguration*/
        source.registerCorsConfiguration("/**",corsConfiguration);

        return source;
    }
}
