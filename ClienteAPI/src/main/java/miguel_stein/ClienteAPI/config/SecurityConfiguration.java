package miguel_stein.ClienteAPI.config;

import miguel_stein.ClienteAPI.security.CustomUserDetailService;
import miguel_stein.ClienteAPI.service.UsuarioService;
import org.springframework.beans.factory.aspectj.ConfigurableObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ConfigurableObject configurableObject) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(config -> config.loginPage("/login").permitAll())
                .httpBasic(withDefaults())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
                    auth.requestMatchers(HttpMethod.DELETE, "/clientes/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/clientes/**").hasRole("ADMIN");
                    auth.requestMatchers("/login/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService) {
        return new CustomUserDetailService(usuarioService);
    }
}
