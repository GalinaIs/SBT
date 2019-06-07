package com.sbt.async.topicexample;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;


public class TopicProducer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TopicExamplesConfig.class, args);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Topic topic = context.getBean(Topic.class);

        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(topic);

            Message message = session.createTextMessage("My test message " + System.currentTimeMillis());
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
