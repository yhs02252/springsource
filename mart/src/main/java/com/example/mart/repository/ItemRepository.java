package com.example.mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mart.entitty.item.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
