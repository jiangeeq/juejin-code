package com.jls.example.order.dao;

import com.jls.example.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    List<Order> findAllByStatusAndCreatedDateBefore(String status, ZonedDateTime dateTime);

    Order findOneByUuid(String uuid);
}
