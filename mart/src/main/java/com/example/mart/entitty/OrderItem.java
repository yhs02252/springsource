package com.example.mart.entitty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@ToString(exclude = { "item", "order" })
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "mart_order_item_seq_gen", sequenceName = "order_item_seq", allocationSize = 1)
@Entity
public class OrderItem {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mart_order_item_seq_gen")
    private Long id;

    private int price;

    private int count;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Item item;

}
