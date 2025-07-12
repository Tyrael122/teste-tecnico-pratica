package org.contoso.cotacoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
public class CotacoesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CotacoesApplication.class, args);
    }

}
