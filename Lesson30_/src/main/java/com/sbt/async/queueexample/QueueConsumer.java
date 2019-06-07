package com.sbt.async.queueexample;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;


public class QueueConsumer {

    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(QueueExamplesConfig.class, args);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Queue queue = context.getBean(Queue.class);

        try (Connection connection = connectionFactory.createConnection()) {
            // Если мы читаем сообщения, то не забываем делать start у соединения
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            MessageConsumer consumer = session.createConsumer(queue);

            // Смотрим какие бывают receive
            TextMessage message = (TextMessage) consumer.receive();

//            consumer.setMessageListener(receivedMessage -> {
//                try {
//                    System.out.println("Message received: " + ((TextMessage) receivedMessage).getText());
//
//                    synchronized (LOCK) {
//                        LOCK.notifyAll();
//                    }
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            });
//
//            synchronized (LOCK) {
//                try {
//                    System.out.println("Waiting for message");
//
//                    LOCK.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

            System.out.println(message.getText());

            // message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
