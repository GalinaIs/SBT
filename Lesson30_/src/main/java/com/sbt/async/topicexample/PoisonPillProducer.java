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


public class PoisonPillProducer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TopicExamplesConfig.class, args);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
        Topic topic = context.getBean(Topic.class);

        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(topic);

            Message testMessage = session.createTextMessage("Test");
            producer.send(testMessage);

            Message poisonPill = session.createMessage();
            // Обратить внимание на то, что poisonPill - сообщение без тела, только свойства и заголовки.
            poisonPill.setBooleanProperty("stop", true);
            producer.send(poisonPill);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
