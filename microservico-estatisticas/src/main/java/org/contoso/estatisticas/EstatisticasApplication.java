package org.contoso.estatisticas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EstatisticasApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstatisticasApplication.class, args);
    }

}
