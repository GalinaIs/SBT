package com.sbt.async.topicexample;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;


public class PoisonPillConsumer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TopicExamplesConfig.class, args);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Topic topic = context.getBean(Topic.class);

        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(topic);

            connection.start();
            do {
                Message message = consumer.receive();

                System.out.println("Received message: " + message);

                if (message.getBooleanProperty("stop")) {
                    break;
                }
            } while (true);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
