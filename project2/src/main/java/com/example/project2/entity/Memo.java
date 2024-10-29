package com.example.project2.entity;

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

// entity 패키지명 하단에 작성하는 클래스는 테이블 정의하는 것과 동일함
// memo 테이블 생성
// 메모번호, 메모내용

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@SequenceGenerator(name = "memo_seq_gen", sequenceName = "memo_seq", allocationSize = 1)
@Entity
public class Memo {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memo_seq_gen")
    @Id // Entity를 만들 때 반드시 들어와야함 == Primary Key
    private Long mno; // Long => Number(19)

    @Column(length = 200, nullable = false)
    private String memoText; // String => Varchar(255)
}