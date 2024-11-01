package com.example.project3.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = "team")
@Table(name = "team_member")
@Entity // Entity 를 @Qeury 사용할 때 여기서 지정한 이름으로 사용해야함
public class Member {

    @Id
    private String id;

    private String userName;

    // 외래키 표현
    @ManyToOne(cascade = CascadeType.ALL) // 관계 주인 선언 : @ManyToOne 을 설정한 Entity가 기준점임을 선언
    private Team team;
}
