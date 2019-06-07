package com.sbt.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class MessageSender {

	@Autowired
	JmsTemplate jmsTemplate;
    @Autowired
    private Topic topic;

	public void sendMessage(final com.sbt.model.Message message) {

		jmsTemplate.send(new MessageCreator(){
				@Override
				public Message createMessage(Session session) throws JMSException{
                    MessageProducer producer = session.createProducer(topic);
                    return session.createObjectMessage(message);
				}
			});
	}

}
