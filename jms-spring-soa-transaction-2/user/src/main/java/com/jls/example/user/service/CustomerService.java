package com.jls.example.user.service;

import com.jls.example.dto.OrderDTO;
import com.jls.example.user.dao.CustomerRepository;
import com.jls.example.user.dao.PayInfoRepository;
import com.jls.example.user.domain.Customer;
import com.jls.example.user.domain.PayInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jiangpeng on 2018/10/9.
 */
@Service
public class CustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PayInfoRepository payInfoRepository;

    @Transactional
    @JmsListener(destination = "order:pay", containerFactory = "msgFactory")
    public void handleOrderPay(OrderDTO dto) {
        LOG.info("get new order for pay:{}", dto);

        PayInfo pay = payInfoRepository.findOneByOrderId(dto.getId());
        if (pay != null) {
            LOG.warn("order already paid: {}", dto);
        }


        Customer customer = customerRepository.findOne(dto.getCustomerId());
        if (customer.getDeposit() < dto.getAmount()) {
            LOG.info("余额不足");
            dto.setStatus("NOT_ENOUGH_DEPOSIT");
            jmsTemplate.convertAndSend("order:ticket_error",dto);
            return;
        }

        pay = new PayInfo();
        pay.setOrderId(dto.getId());
        pay.setAmount(dto.getAmount());
        pay.setStatus("PAID");
        payInfoRepository.save(pay);
        customerRepository.charge(customer.getId(), dto.getAmount());    // 防止高并发

        dto.setStatus("PAID");
        jmsTemplate.convertAndSend("order:ticket_move", dto);
    }
}
