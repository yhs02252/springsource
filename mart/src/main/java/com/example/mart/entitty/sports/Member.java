package com.example.mart.entitty.sports;

import com.example.mart.entitty.item.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "sports_member")
@Getter
@Setter
@ToString(exclude = "lockerList")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "sports_member")
@SequenceGenerator(name = "sports_member_seq_gen", sequenceName = "sports_member_seq", allocationSize = 1)
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sports_member_seq_gen")
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    private Locker lockerList;
}
