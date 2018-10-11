package com.imooc.example.order.dao;

import com.imooc.example.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    Order findOneByUuid(String uuid);
}
