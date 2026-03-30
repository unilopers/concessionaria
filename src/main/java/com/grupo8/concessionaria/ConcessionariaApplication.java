package com.grupo8.concessionaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ConcessionariaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConcessionariaApplication.class, args);
    }

}
