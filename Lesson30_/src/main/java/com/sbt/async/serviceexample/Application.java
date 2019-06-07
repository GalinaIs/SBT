package com.sbt.async.serviceexample;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationConfig.class, args);

        SimpleService simpleService = context.getBean(SimpleService.class);
        simpleService.doSomething();
    }
}
