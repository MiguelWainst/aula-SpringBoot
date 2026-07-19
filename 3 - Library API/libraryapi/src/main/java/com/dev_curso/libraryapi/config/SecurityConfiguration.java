package com.dev_curso.libraryapi.config;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.aspectj.ConfigurableObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.*;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                .csrf(AbstractHttpConfigurer::disable) // Desabilitar o csrf faz com que a minha aplicação possa receber o front de outros lugares.
                .formLogin(config -> config.loginPage("/login").permitAll())
                .httpBasic(withDefaults())
                .authorizeHttpRequests(authorize -> {
                    /*
                    Para permitir ou negar a uma role a permissão de uma http request
                    como um POST ou um GET.
                     */
                    authorize.requestMatchers(HttpMethod.DELETE, "/autores/**").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.DELETE, "/livros/**").hasRole("ADMIN");

                    authorize.requestMatchers("/login").permitAll();
                    authorize.requestMatchers("/autores/**").hasRole("ADMIN");
                    authorize.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN");

                    /*
                     Dita que qualquer requisição feita para esta API tem que estar autenticado.
                     Essa linha deve ser a última, pois qualquer regra abaixo dela será ignorada.
                     */
                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails user1 = User.builder()
                .username("user")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("admin")
                .password(encoder.encode("321"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

}
