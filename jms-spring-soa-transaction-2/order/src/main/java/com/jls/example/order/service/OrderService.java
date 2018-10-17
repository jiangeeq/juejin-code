package com.jls.example.order.service;

import com.jls.example.dto.OrderDTO;
import com.jls.example.order.dao.OrderRepository;
import com.jls.example.order.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

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

    @JmsListener(destination = "order:finish", containerFactory = "msgFactory")
    public void handleOrderFinish(OrderDTO dto) {
        LOG.info("get order for finish:{}", dto);
        Order order = orderRepository.findOne(dto.getId());
        order.setStatus("FINISH");
        orderRepository.save(order);
    }

    /**
     * order错误的情况
     * 1. 一开始索票失败
     * 2. 扣费失败后，解锁票
     *
     * @param dto
     */
    @JmsListener(destination = "order:fail", containerFactory = "msgFactory")
    public void handleOrderFail(OrderDTO dto) {
        LOG.info("get order for fail:{}", dto);
        Order order;
        if (dto.getId() == null) {
            order = createOrder(dto);
            order.setReason("TICKET_LOCK_FAIL");
        } else {
            order = orderRepository.findOne(dto.getId());
            if (dto.getStatus().equals("NOT_ENOUGH_DEPOSIT")) {
                order.setReason("NOT_ENOUGH_DEPOSIT");
            }
            if (dto.getStatus().equals("TIMEOUT")) {
                order.setReason(" ");
            }
        }
        order.setStatus("FAIL");
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

    @Scheduled(fixedDelay = 10000L)
    public void checkTimeOutOrders() {
        ZonedDateTime checkTime = ZonedDateTime.now().minusMinutes(1L);
        List<Order> orders = orderRepository.findAllByStatusAndCreatedDateBefore("NEW", checkTime);
        orders.forEach(order -> {
            LOG.info("order timeout: {}", order);
            OrderDTO dto = new OrderDTO();
            dto.setId(order.getId());
            dto.setTicketNum(order.getTicketNum());
            dto.setUuid(order.getUuid());
            dto.setAmount(order.getAmount());
            dto.setTitle(order.getTitle());
            dto.setCustomerId(order.getCustomerId());
            dto.setStatus("TIMEOUT");
            jmsTemplate.convertAndSend("order:fail", dto);
        });
    }
}
