package com.example.todo.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "todotbl")
@SequenceGenerator(name = "todo_seq_gen", sequenceName = "todo_seq", allocationSize = 1)
public class ToDo {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_seq_gen")
    @Column(name = "todo_id")
    @Id
    private Long id;

    @ColumnDefault("0")
    private boolean completed;

    @ColumnDefault("0")
    private boolean importatnt;

    private String title;

}
