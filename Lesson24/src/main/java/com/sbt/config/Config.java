package com.sbt.config;

import com.sbt.dao.enrich.RecipeEnricher;
import com.sbt.dao.enrich.RecipeEnricherImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("com.sbt")
public class Config {
    @Bean
    public DriverManagerDataSource dataSource() {
        return new DriverManagerDataSource("jdbc:h2:tcp://localhost/~/test", "sa", "");
    }
}
