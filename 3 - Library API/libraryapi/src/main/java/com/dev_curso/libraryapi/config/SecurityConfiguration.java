package com.dev_curso.libraryapi.config;

import com.dev_curso.libraryapi.security.CustomUserDetailService;
import com.dev_curso.libraryapi.service.UsuarioService;
import org.springframework.beans.factory.aspectj.ConfigurableObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

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

                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
                    authorize.requestMatchers("/login/**").permitAll();
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
    public UserDetailsService userDetailsService(UsuarioService usuarioService) {
        return new CustomUserDetailService(usuarioService);
    }

}
