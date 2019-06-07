package com.sbt.async.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;


public class TransactionConsumer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationConfig.class, args);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Queue queue = context.getBean(Queue.class);

        try (Connection connection = connectionFactory.createConnection()) {
            connection.start();

            Session session = connection.createSession(true, 0);

            MessageConsumer consumer = session.createConsumer(queue);

            TextMessage message1 = (TextMessage) consumer.receive();
            System.out.println(message1.getText());

             //if (true) throw new RuntimeException();

            TextMessage message2 = (TextMessage) consumer.receive();
            System.out.println(message2.getText());

            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
