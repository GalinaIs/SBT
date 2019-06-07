package com.sbt.configuration;

import java.util.Arrays;

import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Topic;

@Configuration
public class MessagingConfiguration {

	private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";
	
	private static final String QUEUE = "message-queue";

	@Bean
	public ActiveMQConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
		connectionFactory.setTrustedPackages(Arrays.asList("com.sbt"));
		return connectionFactory;
	}

	@Bean
	public Topic destination() {
		return new ActiveMQTopic("CHAT_TOPIC");
	}
	
	@Bean 
	public JmsTemplate jmsTemplate(){
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
		template.setDefaultDestinationName(QUEUE);
		return template;
	}
	
}
