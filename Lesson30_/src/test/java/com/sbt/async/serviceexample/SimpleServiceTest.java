package com.sbt.async.serviceexample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleServiceTest {

    @Mock
    private ConnectionFactory connectionFactory;

    @Mock
    private Connection connection;

    @Mock
    private Topic topic;

    @Mock
    private Session session;

    @Mock
    private MessageProducer messageProducer;

    @Before
    public void setUp() throws JMSException {
        when(connectionFactory.createConnection()).thenReturn(connection);
        when(connection.createSession(anyBoolean(), anyInt())).thenReturn(session);
        when(session.createProducer(topic)).thenReturn(messageProducer);
    }

    @After
    public void tearDown() throws JMSException {
        verify(connection).close();
    }

    @Test
    public void testDoSomething() throws JMSException {
        TextMessage message = mock(TextMessage.class);
        when(session.createTextMessage("My test message")).thenReturn(message);

        SimpleService service = new SimpleService(connectionFactory, topic);
        service.doSomething();

        verify(messageProducer).send(same(message));
    }
}
