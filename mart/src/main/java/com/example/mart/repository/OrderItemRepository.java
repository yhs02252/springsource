package com.example.mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mart.entitty.item.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
