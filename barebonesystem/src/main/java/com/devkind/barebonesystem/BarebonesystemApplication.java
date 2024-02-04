package com.devkind.barebonesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BarebonesystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarebonesystemApplication.class, args);
    }

}
