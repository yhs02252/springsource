package com.example.mart.entitty.sports;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "sport_locker_seq_gen", sequenceName = "locker_seq", allocationSize = 1)
@Table(name = "sports_locker")
public class Locker {

    @Id
    @Column(name = "locker_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sport_locker_seq_gen")
    private Long id;

    private String name;
}
