package com.jls.example.order.dao;

import com.jls.example.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    Order findOneByUuid(String uuid);
}
