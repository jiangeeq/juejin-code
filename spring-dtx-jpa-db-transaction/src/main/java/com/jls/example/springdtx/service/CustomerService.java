package com.jls.example.springdtx.service;

import com.jls.example.springdtx.dao.CustomerRepository;
import com.jls.example.springdtx.domain.Customer;
import com.jls.example.springdtx.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("customerService")
public class CustomerService {
    private static Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    @Qualifier("orderJdbcTemplate")
    private JdbcTemplate orderJdbcTemplate;

    private static final String INSERT_ORDER_SQL = "insert into customer_order(customer_id,title,amount) values (?,?,?)";

    @Transactional  //事务管理注解
    public void createOrder(Order order) {
        Customer customer = customerRepository.getOne(order.getCustomerId());
        customer.setDeposit(customer.getDeposit() - order.getAmount());
        customerRepository.save(customer);
        if (order.getTitle().contains("error1")) {     //模拟异常出现
            throw new RuntimeException("error1");
        }
        orderJdbcTemplate.update(INSERT_ORDER_SQL, order.getCustomerId(), order.getTitle(), order.getAmount());  //没有使用事务，直接提交
        if (order.getTitle().contains("error2")) {    //模拟异常出现
            throw new RuntimeException("error2");
        }
    }

    public Map userInfo(Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        List orders = orderJdbcTemplate.queryForList("select * from customer_order where customer_id = " + customerId);
        Map result = new HashMap();
        result.put("customer", customer);
        result.put("orders", orders);
        return result;
    }
}

