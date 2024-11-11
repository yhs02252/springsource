package com.example.mart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.mart.entitty.item.Item;
import com.example.mart.entitty.item.Member;
import com.example.mart.entitty.item.Order;
import com.example.mart.entitty.item.QMember;
import com.querydsl.jpa.JPQLQuery;

public class QueryDslOrderRepositoryImpl extends QuerydslRepositorySupport implements QueryDslOrderRepository {

    public QueryDslOrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Member> members() {
        // select * from member where name = 'member1' order by name desc

        // from member where name = 'member1' order by name desc 까지의 구문 입력

        QMember qMember = QMember.member;
        JPQLQuery<Member> query = from(qMember); // ← from member

        // ↓ where name = 'member1' order by name desc ↓
        query.where(qMember.name.eq("member1")).orderBy(qMember.name.desc());

        // ↓ select * ↓
        JPQLQuery<Member> tuple = query.select(qMember);
        System.out.println(tuple);

        return tuple.fetch(); // JPQLQuery<Member> tuple을 List<Member> 타입으로 변환
    }

    @Override
    public List<Item> items() {
        return null;
    }

    @Override
    public List<Object[]> joinTest() {
        return null;
    }
}
