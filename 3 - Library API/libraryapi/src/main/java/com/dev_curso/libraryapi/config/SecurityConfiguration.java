package com.dev_curso.libraryapi.config;

import com.dev_curso.libraryapi.security.CustomUserDetailService;
import com.dev_curso.libraryapi.service.UsuarioService;
import org.springframework.beans.factory.aspectj.ConfigurableObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) // Comentar sobre isso
public class SecurityConfiguration {

    /*
    o metod statico "withDefaults()" é usado com um import statico.* Ele serve
    pra deixar algo sem configuração ou default mesmo.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Desabilitar o csrf faz com que a minha aplicação possa receber o front de outros lugares.
//                .formLogin(config -> config.loginPage("/login").permitAll())
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
                    authorize.requestMatchers("/login/**").permitAll();
                    /*
                     Dita que qualquer requisição feita para esta API tem que estar autenticado.
                     Essa linha deve ser a última, pois qualquer regra abaixo dela será ignorada.
                     */
                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

//    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService) {
        return new CustomUserDetailService(usuarioService);
    }

    // Tira o prefixo "ROLE_" das roles
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
