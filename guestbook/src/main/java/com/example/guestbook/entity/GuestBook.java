package com.example.guestbook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@SequenceGenerator(name = "guestbook_seq_gen", sequenceName = "guestbook_seq", allocationSize = 1)
@Entity
public class GuestBook extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guestbook_seq_gen")
    @Id
    @Column(name = "guestbook_id")
    private Long gno;

    @Column(length = 150, nullable = false)
    private String title;

    @Column(length = 50, nullable = false)
    private String writer;

    @Column(length = 1500, nullable = false)
    private String content;
}
