package com.sbt.async.queueexample;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;


public class QueueProducer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(QueueExamplesConfig.class, args);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Queue queue = context.getBean(Queue.class);

        try (Connection connection = connectionFactory.createConnection()) {
            // Смотрим аргументы метода createSession
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer producer = session.createProducer(queue);

            Message message = session.createTextMessage("My test message " + System.currentTimeMillis());
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
