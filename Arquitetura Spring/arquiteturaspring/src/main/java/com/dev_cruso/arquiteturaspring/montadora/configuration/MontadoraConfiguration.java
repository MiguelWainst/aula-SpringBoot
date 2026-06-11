package com.dev_cruso.arquiteturaspring.montadora.configuration;

import com.dev_cruso.arquiteturaspring.montadora.Motor;
import com.dev_cruso.arquiteturaspring.montadora.TipoMotor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MontadoraConfiguration {

    @Bean(name = "motorAspirado") //Não é obrigatório, mas da pra definir outro nome pro bean
    public Motor motorAspirado() {
        var motor = new Motor();
        motor.setCavalos(120);
        motor.setCilindros(4);
        motor.setModelo("XPTO-0");
        motor.setLitragem(2.0);
        motor.setTipoMotor(TipoMotor.ASPIRADO);
        return motor;
    }

    @Bean
    public Motor motorEletrico() {
        var motor = new Motor();
        motor.setCavalos(110);
        motor.setCilindros(3);
        motor.setModelo("TH-10");
        motor.setLitragem(1.4);
        motor.setTipoMotor(TipoMotor.ELETRICO);
        return motor;
    }

    @Bean
    @Primary // Diz que esse é o padrão, se eu não expecificar no Controller qual é, ele usa esse
    public Motor motorTurbo() {
        var motor = new Motor();
        motor.setCavalos(180);
        motor.setCilindros(4);
        motor.setModelo("XPTO-0");
        motor.setLitragem(1.5);
        motor.setTipoMotor(TipoMotor.TURBO);
        return motor;
    }
}
