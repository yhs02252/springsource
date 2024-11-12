package com.example.mart.repository;

import java.util.List;

import com.example.mart.entitty.item.Item;
import com.example.mart.entitty.item.Member;

public interface QueryDslOrderRepository {

    List<Member> members();

    List<Item> items();

    List<Object[]> joinTest();

    List<Object[]> threeJoinTest();

    List<Object[]> subQueryTest();

}
