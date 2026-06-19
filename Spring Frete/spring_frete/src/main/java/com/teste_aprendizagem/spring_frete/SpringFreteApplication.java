package com.teste_aprendizagem.spring_frete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringFreteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringFreteApplication.class, args);
	}

}
