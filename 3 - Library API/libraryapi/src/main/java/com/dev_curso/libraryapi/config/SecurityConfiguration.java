package com.dev_curso.libraryapi.config;

import org.springframework.beans.factory.aspectj.ConfigurableObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /*
    o metod statico "withDefaults()" é usado com um import statico.* Ele serve
    pra deixar algo sem configuração ou default mesmo.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ConfigurableObject configurableObject) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Desabilitar o csrf faz com que minha aplicação possa receber o front de outros lugares.
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.anyRequest().authenticated(); // Dita que qualquer requisição feita para esta API tem que estar autenticado
                })
                .build();
    }
}
