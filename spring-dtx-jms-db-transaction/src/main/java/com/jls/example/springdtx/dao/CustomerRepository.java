package com.jls.example.springdtx.dao;

import com.jls.example.springdtx.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
