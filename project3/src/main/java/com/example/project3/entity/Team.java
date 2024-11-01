package com.example.project3.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@ToString(exclude = "members")
@Entity
public class Team {

    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL) // 어느 Entity가 기준(기본적으로는 Many쪽)으로 작동하는지 명시해줘야함 = mappedBy,
    // @OneToMany(mappedBy = "team", fetch = FetchType.EAGER) // fetch
    @Builder.Default // List구조일때 필요
    private List<Member> members = new ArrayList<>(); // 여러개의 객체가 상속되기 때문에 List 구조로 만들어줘야함
    // lazy 방식 ==> left join 을 하지 않음 => member 정보 없음
}
