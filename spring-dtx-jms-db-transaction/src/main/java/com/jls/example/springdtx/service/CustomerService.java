package com.jls.example.springdtx.service;

import com.jls.example.springdtx.dao.CustomerRepository;
import com.jls.example.springdtx.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("customerService")
public class CustomerService {
    private static Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    @JmsListener(destination = "customer:msg:new")
    public void handle(String msg) {
        logger.info("get customer:new:msg():{}", msg);
        Customer customer = new Customer();
        customer.setUsername(msg);
        customer.setDeposit(100);
        customerRepository.save(customer);
        if(msg.contains("error1")){
            throw new RuntimeException("error1");
        }
    }

    @Transactional
    public Customer create(Customer customer) {
        logger.info("CustomerService create customer:{}", customer.getUsername());
        customer = customerRepository.save(customer);
        if (customer.getUsername().contains("error1")) {
            throw new RuntimeException("error1");
        }
        jmsTemplate.convertAndSend("customer:msg:reply", customer.getUsername());
        if (customer.getUsername().contains("error2")) {
            throw new RuntimeException("error2");
        }
        return customer;
    }

}