package com.sbt.async.serviceexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SimpleServiceEmbeddedBrokerTest {

    @Autowired
    private SimpleService service;

    @Test
    public void testDoSomething() {
        service.doSomething();
    }

}
