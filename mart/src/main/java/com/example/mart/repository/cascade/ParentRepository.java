package com.example.mart.repository.cascade;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mart.entitty.cascade.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {

}
