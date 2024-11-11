package com.example.jpql.repository;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.example.jpql.jpql.Address;
import com.example.jpql.jpql.Member;
import com.example.jpql.jpql.Order;
import com.example.jpql.jpql.Product;
import com.example.jpql.jpql.QMember;
import com.example.jpql.jpql.QProduct;
import com.example.jpql.jpql.Team;
import com.querydsl.core.BooleanBuilder;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private JpqlMemberRepository jpqlMemberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void insertTest() {

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Team team = Team.builder().name("team" + i).build();
            teamRepository.save(team);

            Member member = Member.builder().name("member" + i).age(20 + i).team(team).build();
            jpqlMemberRepository.save(member);

            Product product = Product.builder().name("제품" + i).price(i * 10000).stockAmount(i * 5).build();
            productRepository.save(product);
        });
    }

    @Test
    public void insertOrderTest() {

        // 2번 member 가 3번 제품을 주문한다
        Address address = new Address();
        address.setCity("서울시");
        address.setStreet("152-1");
        address.setZipcode("11017");

        LongStream.rangeClosed(1, 3).forEach(i -> {
            Order order = Order.builder()
                    .address(address)
                    .orderAmount(15)
                    .member(Member.builder().id(2L).build())
                    .product(Product.builder().id(i).build())
                    .build();
            orderRepository.save(order);
        });
    }

    @Test
    public void testFindAllMembers() {
        // jpqlMemberRepository.findAllMembers().forEach(m -> System.out.println(m));

        // 전체 조회(오름차순)
        // System.out.println(jpqlMemberRepository.findAllMembers());

        // 다른 컬럼 정렬(오름차순 - 기본)
        // System.out.println(jpqlMemberRepository.findAllMembers(Sort.by("age")));

        // 다른 컬럼 정렬(내림차순 - DESC)
        // System.out.println(jpqlMemberRepository.findAllMembers(Sort.by(Sort.Direction.DESC,
        // "age")));

        // 구문 2개 출력 -> 문자열로 변환 필요
        // System.out.println(jpqlMemberRepository.findMembers2()); // 정상적 출력 불가
        List<Object[]> list = jpqlMemberRepository.findMembers2();

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
            System.out.printf("name = %s, age = $d\n", objects[0], objects[1]);
        }
    }

    @Test
    public void testAddress() {
        System.out.println(orderRepository.findByAddresses());
    }

    @Test
    public void TestOrders() {
        List<Object[]> list = orderRepository.findByOrders();

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects)); // 전체 결과 뽑기

            // 결과값 나누기
            Member member = (Member) objects[0];
            Product product = (Product) objects[1];
            int orderAmount = (Integer) objects[2];

            System.out.println(member);
            System.out.println(product);
            System.out.println(orderAmount);
        }
    }

    @Test
    public void memberQueryTest() {
        // System.out.println(jpqlMemberRepository.findByNameContaining("member5"));
        // System.out.println(jpqlMemberRepository.findByAgeGreaterThan(27));
        // System.out.println(jpqlMemberRepository.findByTeam(Team.builder().id(2L).build()));

        List<Object[]> list = jpqlMemberRepository.aggregate();

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));

            System.out.println("나이 합계 : " + objects[0]);
            System.out.println("나이 평균 : " + objects[1]);
            System.out.println("연장자 : " + objects[2]);
            System.out.println("최연소 : " + objects[3]);
            System.out.println("인원수 : " + objects[4]);
        }
    }

    @Test
    public void teamJoinTest() {
        // System.out.println(jpqlMemberRepository.findByTeam2("team2"));

        // List<Object[]> list3 = jpqlMemberRepository.findByTeam3("team3");
        // for (Object[] objects : list3) {
        // System.out.println(Arrays.toString(objects));
        // System.out.println("멤버 정보 : " + objects[0]);
        // System.out.println("팀 정보 : " + objects[1]);
        // }

        // List<Object[]> list4 = jpqlMemberRepository.findByTeam4("team4");
        // for (Object[] objects : list4) {
        // System.out.println(Arrays.toString(objects));
        // System.out.println("멤버 정보 : " + objects[0]);
        // System.out.println("팀 정보 : " + objects[6]);
        // }

        List<Object[]> list5 = jpqlMemberRepository.findByTeam5("team5");
        for (Object[] objects : list5) {
            System.out.println(Arrays.toString(objects));
            System.out.println("멤버 정보 : " + objects[0]);
            System.out.println("팀 정보 : " + objects[1]);
        }
    }

    // QeuryDSL 테스트
    @Test
    public void queryDSLTest() {
        QProduct qProduct = QProduct.product; // = new QProduct("product") 와 같음
        QMember qMember = QMember.member;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 상품명이 제품 1인 상품 조회
        // Iterable<Product> products =
        // productRepository.findAll(qProduct.name.eq("제품1"));

        // 상품명이 제품 1이고, 가격이 500 초과인 상품 조회
        // Iterable<Product> products = productRepository.findAll(
        // qProduct.name.eq("제품1")
        // .and(qProduct.price.gt(500)));

        // 상품명이 제품 1이고, 가격이 500 이상인 상품 조회
        // Iterable<Product> products = productRepository.findAll(
        // qProduct.name.eq("제품1")
        // .and(qProduct.price.goe(500)));

        // 상품명에 '제품' 글자가 들어있는 상품 조회
        // Iterable<Product> products = productRepository.findAll(
        // qProduct.name.contains("제품"));

        // 상품명이 '제품'으로 시작하는 상품 조회
        // Iterable<Product> products = productRepository.findAll(
        // qProduct.name.startsWith("제품"));

        // 상품명이 '3'으로 끝나는 상품 조회
        // Iterable<Product> products =
        // productRepository.findAll(qProduct.name.endsWith("3"));
        // for (Product product : products) {
        // System.out.println(product);
        // }

        // member name 이 member3인 회원 조회
        // Iterable<Member> members =
        // jpqlMemberRepository.findAll(qMember.name.eq("member3"));

        // BooleanBuilder 활용

        // member name이 member3인 회원 조회(id 기준으로)
        // booleanBuilder.and(qMember.name.eq("member3"));
        // Iterable<Member> members = jpqlMemberRepository.findAll(booleanBuilder,
        // Sort.by("id").descending());
        // for (Member member : members) {
        // System.out.println(member);
        // }

        // 상품명이 제품 1이고, 가격이 500 초과인 상품 조회
        booleanBuilder.and(qProduct.name.eq("제품1")).and(qProduct.price.gt(500));
        Iterable<Product> products = productRepository.findAll(booleanBuilder);
        for (Product product : products) {
            System.out.println(product);
        }

    }
}
