package org.contoso.operacoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OperacoesApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperacoesApplication.class, args);
    }

}
