package com.jls.example.springdtx.service;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
        TransactionAwareConnectionFactoryProxy proxy = new TransactionAwareConnectionFactoryProxy();
        proxy.setTargetConnectionFactory(cf);
        proxy.setSynchedLocalTransactionAllowed(true);
        return proxy;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }
}
