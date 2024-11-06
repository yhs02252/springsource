package com.example.mart.entitty.item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import com.example.mart.entitty.constant.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@ToString(exclude = { "member", "orderItemList", "delivery" })
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "mart_orders")
@SequenceGenerator(name = "mart_order_seq_gen", sequenceName = "order_seq", allocationSize = 1)
@Entity
public class Order extends BaseEntity {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mart_order_seq_gen")
    private Long id;

    @CreatedDate
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // 이 구문이 없다면 테이블에 입력될 때 0, 1 이 입력됨
    private OrderStatus status;

    @ManyToOne
    private Member member;

    // OrderItem ==> Order 접근하는 관계는 OrderItem 쪽에 설정
    // 이유는 외래키가 있는 쪽에 관계 설정 해야함

    // Order ==> OrderItem 접근하기
    // fetch = FetchType.EAGER
    @Builder.Default
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItemList = new ArrayList<>();

    @OneToOne
    private Delivery delivery;
}
