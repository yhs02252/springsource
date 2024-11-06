package com.example.mart.entitty.product;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorColumn(name = "DTYPE") // 상속될 컬럼별 명칭나열 컬럼 생성
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속될 컬럼들 테이블 포함 타입 결정
public abstract class Product {

    @SequenceGenerator(name = "product_seq_gen", sequenceName = "product_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
    @Id
    @Column(name = "product_id")
    private Long id;

    private String name;

    private int price;
}
