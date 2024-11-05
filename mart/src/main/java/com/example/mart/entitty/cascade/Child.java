package com.example.mart.entitty.cascade;

import java.util.ArrayList;
import java.util.List;

import com.example.mart.entitty.item.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Child extends BaseEntity {

    @SequenceGenerator(name = "child_seq_gen", sequenceName = "child_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "child_seq_gen")
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Parent parent;
}
