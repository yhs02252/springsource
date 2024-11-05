package com.example.mart.entitty.item;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "orderList")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "mart_member_seq_gen", sequenceName = "member_seq", allocationSize = 1)
@Table(name = "mart_member")
@Entity
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mart_member_seq_gen")
    private Long id;

    private String name;

    private String zipcode;

    private String city;

    private String street;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Order> orderList = new ArrayList<>();

}
