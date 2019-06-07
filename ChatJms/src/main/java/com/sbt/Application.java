package com.sbt;

import com.sbt.repository.UserRepository;
import com.sbt.repository.UserRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Bean
    public UserRepository getUserRepo() {
        return new UserRepositoryImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
