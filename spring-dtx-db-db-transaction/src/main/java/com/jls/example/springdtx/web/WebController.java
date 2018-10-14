package com.jls.example.springdtx.web;

import com.jls.example.springdtx.domain.Order;
import com.jls.example.springdtx.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WebController {
    @Autowired
    @Qualifier("customerService")
    private CustomerService customerService;

    @GetMapping(value = "/test")
    @ResponseBody
    public String createOrder(Integer order){
        return "测试成功";
    }

    @PostMapping("/customer/order")
    public void createOrder(@RequestBody Order order){
        customerService.createOrder(order);
    }
}
