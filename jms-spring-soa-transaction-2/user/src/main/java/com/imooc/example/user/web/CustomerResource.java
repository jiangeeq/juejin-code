package com.jls.example.user.web;

import com.jls.example.dto.OrderDTO;
import com.jls.example.user.dao.CustomerRepository;
import com.jls.example.user.domain.Customer;
import com.jls.example.user.feign.OrderClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mavlarn on 2018/1/20.
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @PostConstruct
    public void init() {
        Customer customer = new Customer();
        customer.setUsername("jls");
        customer.setPassword("111111");
        customer.setRole("User");
        customerRepository.save(customer);
    }

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderClient orderClient;

    @PostMapping("")
    public Customer create(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("")
    @HystrixCommand
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @GetMapping("/my")
    @HystrixCommand
    public Map getMyInfo() {
        Customer customer = customerRepository.findOneByUsername("jls");
        OrderDTO order = orderClient.getMyOrder(1l);
        Map result = new HashMap();
        result.put("customer", customer);
        result.put("order", order);
        return result;
    }

}
