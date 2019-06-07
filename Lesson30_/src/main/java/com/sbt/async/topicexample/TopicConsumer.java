package com.sbt.async.topicexample;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import static java.lang.Thread.currentThread;


public class TopicConsumer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TopicExamplesConfig.class, args);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Topic topic = context.getBean(Topic.class);

        Thread thread1 = new Thread(new MyTopicConsumer(connectionFactory, topic), "1");
        Thread thread2 = new Thread(new MyTopicConsumer(connectionFactory, topic), "2");
        Thread thread3 = new Thread(new MyTopicConsumer(connectionFactory, topic), "3");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static class MyTopicConsumer implements Runnable {

        private final ConnectionFactory connectionFactory;

        private final Topic topic;

        private MyTopicConsumer(ConnectionFactory connectionFactory, Topic topic) {
            this.connectionFactory = connectionFactory;
            this.topic = topic;
        }

        @Override
        public void run() {
            try (Connection connection = connectionFactory.createConnection()) {
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageConsumer consumer = session.createConsumer(topic);

                connection.start();
                TextMessage message = (TextMessage) consumer.receive();

                System.out.println("Received from thread(" + currentThread().getName() + "): " + message.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
