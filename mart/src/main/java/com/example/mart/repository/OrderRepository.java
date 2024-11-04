package com.example.mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mart.entitty.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
