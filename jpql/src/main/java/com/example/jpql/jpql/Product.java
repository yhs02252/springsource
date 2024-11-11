package com.example.jpql.jpql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@SequenceGenerator(name = "jpql_product_seq_gen", sequenceName = "jpql_product_seq", allocationSize = 1)
@Table(name = "jpql_product")
@Entity
public class Product extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpql_product_seq_gen")
    @Column(name = "product_id")
    @Id
    private Long id;

    private int price;

    private int stockAmount;

    @Column(name = "product_name")
    private String name;
}
