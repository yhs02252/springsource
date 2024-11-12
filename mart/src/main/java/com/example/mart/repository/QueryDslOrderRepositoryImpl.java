package com.example.mart.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.mart.entitty.item.Item;
import com.example.mart.entitty.item.Member;
import com.example.mart.entitty.item.Order;
import com.example.mart.entitty.item.QItem;
import com.example.mart.entitty.item.QMember;
import com.example.mart.entitty.item.QOrder;
import com.example.mart.entitty.item.QOrderItem;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
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
        query.where(qMember.name.eq("사람1")).orderBy(qMember.name.desc());

        // ↓ select * ↓
        JPQLQuery<Member> tuple = query.select(qMember);
        System.out.println(tuple);

        return tuple.fetch(); // JPQLQuery<Member> tuple을 List<Member> 타입으로 변환
    }

    @Override
    public List<Item> items() {
        // select * from item where name ='아파트' and price > 10000

        // from 절
        QItem qItem = QItem.item;
        JPQLQuery<Item> itemQuery = from(qItem);

        // where~ 절
        // select 절
        JPQLQuery<Item> tuple = itemQuery.where(qItem.name.eq("아파트").and(qItem.price.gt(10000))).select(qItem);

        System.out.println("tuple" + tuple);

        // List<> 구문으로 변환 후 리턴
        return tuple.fetch();
    }

    @Override
    public List<Object[]> joinTest() {
        // select * from mart_orders mo join mart_member mm on mo.member_member_id =
        // mm.member_id

        QMember qMember = QMember.member;
        QOrder qOrder = QOrder.order;
        JPQLQuery<com.querydsl.core.Tuple> tuple = from(qOrder).join(qMember).on(qOrder.member.eq(qMember))
                .select(qOrder, qMember);

        System.out.println(tuple);

        // Object[] 배열로 바꾸기
        List<com.querydsl.core.Tuple> result = tuple.fetch();
        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Object[]> threeJoinTest() {

        // SELECT *
        // FROM MART_ORDERS mo
        // JOIN MART_MEMBER mm ON mo.MEMBER_MEMBER_ID = mm.MEMBER_ID
        // LEFT JOIN ORDER_ITEM oi ON mo.ORDER_ID = oi.ORDER_ORDER_ID ;

        QMember qMember = QMember.member;
        QOrder qOrder = QOrder.order;
        QOrderItem qOrderItem = QOrderItem.orderItem;

        JPQLQuery<com.querydsl.core.Tuple> tuple = from(qOrder)
                .join(qMember).on(qOrder.member.eq(qMember))
                .leftJoin(qOrderItem).on(qOrder.eq(qOrderItem.order))
                .select(qOrder, qMember, qOrderItem);

        System.out.println(tuple);

        List<com.querydsl.core.Tuple> result = tuple.fetch();

        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());

        return list;
    }

    @Override
    public List<Object[]> subQueryTest() {

        QMember qMember = QMember.member;
        QOrder qOrder = QOrder.order;
        QOrderItem qOrderItem = QOrderItem.orderItem;

        // select에 사용할 서브쿼리 생성
        JPQLQuery<Long> orderCnt = JPAExpressions.select(qOrderItem.order.count().as("order_cnt")).from(qOrderItem)
                .where(qOrderItem.order.eq(qOrder)).groupBy(qOrderItem.order);

        JPQLQuery<Tuple> tuple = from(qOrder)
                .join(qMember).on(qOrder.member.eq(qMember)).select(qOrder.id, qOrder.status, orderCnt);

        List<Tuple> result = tuple.fetch();
        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());

        return list;
    }
}
