package com.example.mart.entitty.sports;

import com.example.mart.entitty.item.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Getter
@Setter
@ToString(exclude = "sportsMember")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "sport_locker_seq_gen", sequenceName = "locker_seq", allocationSize = 1)
@Table(name = "sports_locker")
public class Locker extends BaseEntity {

    @Id
    @Column(name = "locker_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sport_locker_seq_gen")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "lockerList")
    private Member sportsMember;
}
