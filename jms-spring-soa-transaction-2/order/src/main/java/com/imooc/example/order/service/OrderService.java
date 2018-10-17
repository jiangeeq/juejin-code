package com.jls.example.order.service;

import com.jls.example.dto.OrderDTO;
import com.jls.example.order.dao.OrderRepository;
import com.jls.example.order.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

/**
 * Created by jiangpeng on 2018/10/9.
 */
@Service
public class OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @JmsListener(destination = "order:locked", containerFactory = "msgFactory")
    public void handleOrderNew(OrderDTO dto) {
        LOG.info("get new order to create:{}", dto);
        if (orderRepository.findOneByUuid(dto.getUuid()) != null) {
            LOG.info("msg already processed:{}", dto);
        } else {
            Order order = createOrder(dto);
            orderRepository.save(order);
            dto.setId(order.getId());
        }
        dto.setStatus("NEW");
        jmsTemplate.convertAndSend("order:pay", dto);
    }

    @Transactional
    @JmsListener(destination = "order:finish", containerFactory = "msgFactory")
    public void handleOrderFinish(OrderDTO dto) {
        LOG.info("get order for finish:{}", dto);
        Order order = orderRepository.findOne(dto.getId());
        order.setStatus("FINISH");
        orderRepository.save(order);
    }

    private Order createOrder(OrderDTO dto) {
        Order order = new Order();
        order.setUuid(dto.getUuid());
        order.setAmount(dto.getAmount());
        order.setTitle(dto.getTitle());
        order.setTicketNum(dto.getTicketNum());
        order.setCustomerId(dto.getCustomerId());
        order.setStatus("NEW");
        order.setCreateDate(ZonedDateTime.now());
        return order;
    }
}
