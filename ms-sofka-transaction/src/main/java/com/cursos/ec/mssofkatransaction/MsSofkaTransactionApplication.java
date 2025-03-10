package com.cursos.ec.mssofkatransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsSofkaTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsSofkaTransactionApplication.class, args);
    }

}
