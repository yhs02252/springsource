package com.example.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Getter
@Setter
@ToString(exclude = "board")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "reply_seq_gen", sequenceName = "reply_seq", allocationSize = 1)
@Entity
public class Reply extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reply_seq_gen")
    @Id
    private Long rno;
    @Column(nullable = false)
    private String replyer;
    @Column(nullable = false)
    private String text;

    // fetch type lazy
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
