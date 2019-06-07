package com.sbt.async.serviceexample;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;

@Configuration
@EnableJms
@ComponentScan
class TestConfig {

    @Bean
    public ConnectionFactory jmsFactory() {
        return new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
    }

    @Bean
    public Topic destination() {
        return new ActiveMQTopic("TESTTOPIC");
    }
}
