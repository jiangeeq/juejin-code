package com.jls.example.springdtx.service;

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
    @Qualifier("userJdbcTemplate")
    private JdbcTemplate userJdbcTemplate;

    @Autowired
    @Qualifier("orderJdbcTemplate")
    private JdbcTemplate orderJdbcTemplate;

    private static final String UPDATE_CUSTOMER_SQL = "update customer set deposit = deposit -? where id = ?";
    private static final String INSERT_ORDER_SQL = "insert into customer_order(customer_id,title,amount) values (?,?,?)";

    @Transactional  //事务管理注解
    public void createOrder(Order order) {
        userJdbcTemplate.update(UPDATE_CUSTOMER_SQL, order.getAmount(), order.getCustomerId());
        if (order.getTitle().contains("error1")) {     //模拟异常出现
            throw new RuntimeException("error1");
        }
        orderJdbcTemplate.update(INSERT_ORDER_SQL, order.getCustomerId(), order.getTitle(), order.getAmount());  //没有使用事务，直接提交
        if (order.getTitle().contains("error2")) {    //模拟异常出现
            throw new RuntimeException("error2");
        }
    }

    public Map userInfo(Long customerId) {
        Map customer = userJdbcTemplate.queryForMap("select * from customer where id = " + customerId);
        List orders = orderJdbcTemplate.queryForList("select * from customer_order where customer_id = " + customerId);
        Map result = new HashMap();
        result.put("customer", customer);
        result.put("orders", orders);
        return result;
    }
}

