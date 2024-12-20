package com.example.mart.repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mart.entitty.item.Item;
import com.example.mart.entitty.item.Member;
import com.example.mart.entitty.item.Order;
import com.example.mart.entitty.item.OrderItem;
import com.example.mart.entitty.product.Album;
import com.example.mart.entitty.product.Book;
import com.example.mart.entitty.product.Movie;
import com.example.mart.entitty.constant.DeliveryStatus;
import com.example.mart.entitty.constant.OrderStatus;
import com.example.mart.entitty.item.Delivery;
import com.example.mart.repository.item.DeliveryRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MartRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    // C
    @Test
    public void memberInsertTest() {
        IntStream.rangeClosed(1, 3).forEach(i -> {
            Member member = Member.builder()
                    .id(1L + i)
                    .name("사람" + i)
                    .zipcode("48548-" + i)
                    .city("동네" + i)
                    .street("482" + i + "번 길")
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void itemInsertTest() {
        // IntStream.rangeClosed(1, 3).forEach(i -> {
        // itemRepository.save(Item.builder().id(1L + i).name("상품" +
        // i).price(10000).quantity(i + 30).build());
        // });
        Album album = new Album();
        album.setArtist("로제");
        album.setName("아파트");
        album.setPrice(15200);
        album.setQuantity(15);
        itemRepository.save(album);

        Book book = new Book();
        book.setAuthor("한강");
        book.setIsbn("112ㄱ");
        book.setName("소년이 온다");
        book.setPrice(10000);
        book.setQuantity(15);
        itemRepository.save(book);

        Movie movie = new Movie();
        movie.setDirector("리들리 스콧");
        movie.setActor("폴 메스칼");
        movie.setName("글래디에이터2");
        movie.setPrice(25000);
        movie.setQuantity(300);
        itemRepository.save(movie);
    }

    @Test
    public void orderInsertTest() {
        //

        Member member = memberRepository.findById(1L).get();

        Order order = Order.builder()
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.ORDERED)
                .member(member)
                .build();
        orderRepository.save(order);

        Item item = itemRepository.findById(23L).get();

        OrderItem orderItem = OrderItem.builder()
                .price(item.getPrice())
                .count(2)
                .order(order)
                .item(item)
                .build();

        orderItemRepository.save(orderItem);

        // item 수량 감소

        item.setQuantity(item.getQuantity() - orderItem.getCount());
        itemRepository.save(item);
        orderItem.setPrice(item.getPrice() * orderItem.getCount());
        orderItemRepository.save(orderItem);

    }

    @Test
    public void orderItemInsertTest() {

        Item item = itemRepository.findById(23L).get();
        Order order = Order.builder().id(21L).build();

        OrderItem orderItem = OrderItem.builder()
                .price(50000)
                .count(4)
                .order(order)
                .item(item)
                .build();
        orderItemRepository.save(orderItem);

        // item 수량 감소

        item.setQuantity(item.getQuantity() - orderItem.getCount());
        itemRepository.save(item);
    }

    // R
    @Test
    public void memberAndItemAndOrderListTest() {

        // 주문 내역 조회
        // orderRepository.findAll().forEach(order -> System.out.println(order));

        // 주문 상세 내역 조회
        // orderRepository.findAll().forEach(order -> {
        // System.out.println(order);
        // System.out.println(order.getMember());
        // });

        // orderItemRepository.findAll().forEach(orders -> {
        // System.out.println(orders);
        // System.out.println(orders.getItem());
        // System.out.println(orders.getOrder().getMember());
        // });

        orderItemRepository.findAll().forEach(orders -> {
            // 객체 그래프 탐색
            Item item = orders.getItem();
            Member member = orders.getOrder().getMember();
            System.out.printf("주문 내역 : %s\n주문 상품 : %s\n주문 고객 : %s\n", orders, item, member);
        });
    }

    @Test
    public void memberAndItemAndOrderRowTest() {

        OrderItem orderItem = orderItemRepository.findById(1L).get();

        System.out.println(orderItem);
        System.out.println(orderItem.getItem());
        System.out.println(orderItem.getOrder());
        System.out.println(orderItem.getOrder().getMember());
    }

    // U
    @Test
    public void memberAndItemAndOrderUpdateTest() {

        // 고객의 주소 변경
        // Member member =
        // Member.builder().id(null).name(null).street(null).zipcode(null).build(); <-
        // 하나라도 빠지면 바로 공백으로 이어지기 때문에 추천되지 않음

        Member member = memberRepository.findById(1L).get();
        member.setCity("서울");

        // save : insert or update
        // 'Entity 매니저'가 있어서 현재 Entity가 new 인지 기존 entity 인지 자체적인 구분 가능
        // new : insert 호출 / 기존 entity : update 호출
        memberRepository.save(member);

        // 1번 주문 상품의 아이템(2번 아이템) 가격 변경

        // 아이템 가격 변경
        Item item = itemRepository.findById(2L).get();
        item.setPrice(12500);
        itemRepository.save(item);

        OrderItem orderItem = orderItemRepository.findById(3L).get();
        orderItem.setPrice(item.getPrice() * orderItem.getCount());
        orderItemRepository.save(orderItem);

        // 아이템 수량 변경
        item.setQuantity(item.getQuantity() - orderItem.getCount());
        itemRepository.save(item);
    }

    // D
    @Test
    public void memberAndItemAndOrderDeleteTest() {

        OrderItem orderItem = orderItemRepository.findById(8L).get();
        Long orderItemId = orderItem.getId();

        // 연관된 Entity 작업 롤백
        int orderItemCount = orderItem.getCount();
        Long orderItemItemId = orderItem.getItem().getId();
        Item item = itemRepository.findById(orderItemItemId).get();
        item.setQuantity(item.getQuantity() + orderItemCount);
        itemRepository.save(item);

        // 주문상품 취소
        Long orderId = orderItem.getOrder().getId();
        Order orderStatus = orderRepository.findById(orderId).get();
        orderStatus.setStatus(OrderStatus.CANCLE);
        System.out.println(orderRepository.save(orderStatus));

        orderItemRepository.deleteById(orderItemId);

        // 주문 취소
        orderRepository.deleteById(orderItemId);

    }

    // 양방향
    // Order ==> OrderItem 객체 그래프 탐색
    @Test
    @Transactional
    public void testOrderItemList() {

        Order order = orderRepository.findById(21L).get();
        System.out.println(order);
        order.getOrderItemList().forEach(orderItem -> System.out.println(orderItem));

    }

    @Test
    @Transactional
    public void testOrderList() {

        Member member = memberRepository.findById(1L).get();
        System.out.println(member);

        // Member ==> Order 탐색 시도
        member.getOrderList().forEach(orders -> {
            System.out.println(orders);
        });
    }

    // 일대일

    @Test
    public void deliveryInsertTest() {
        // 배송정보 입력
        Delivery delivery = Delivery.builder()
                .city("서울시")
                .street("71-751길")
                .zipcode("15864")
                .deliveryStatus(DeliveryStatus.READY)
                .build();
        deliveryRepository.save(delivery);

        // order와 배송정보 연결
        Order order = orderRepository.findById(21L).get();
        order.setDelivery(delivery);
        orderRepository.save(order);
    }

    @Test
    public void testOrderRead() {
        // order 조회 (배송정보)
        Order order = orderRepository.findById(21L).get();
        System.out.println(order);
        System.out.println(order.getDelivery());
        System.out.println(order.getDelivery().getDeliveryStatus());
    }

    // 양방향(배송 => 주문)
    @Test
    @Transactional
    public void testDeliveryRead() {
        Delivery delivery = deliveryRepository.findById(1L).get();
        System.out.println(delivery);
        System.out.println(delivery.getOrder());
        System.out.println(delivery.getOrder().getOrderDate());
        System.out.println(delivery.getOrder().getOrderItemList());
    }

    // QeuryDsl 테스트
    @Test
    public void testQueryDslMember() {
        System.out.println(orderRepository.members());
    }

    @Test
    public void testQueryItem() {
        System.out.println(orderRepository.items());
    }

    @Test
    public void testJoinOrderAndMember() {
        // System.out.println(orderRepository.joinTest());
        // [[Ljava.lang.Object;@1ac65143] -> List 형태로 toString 하여 뽑아내야함 : 결과값이 두개라서

        List<Object[]> result = orderRepository.joinTest();

        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));

            System.out.println((Order) objects[0]);
            System.out.println((Member) objects[1]);
        }
    }

    @Test
    public void testThreeLeftJoin() {
        List<Object[]> result = orderRepository.threeJoinTest();

        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));

            // System.out.println(objects[0]);
            // System.out.println(objects[1]);
            // System.out.println(objects[2]);

            System.out.println((Order) objects[0]);
            System.out.println((Member) objects[1]);
            System.out.println((OrderItem) objects[2]);
        }
    }

    @Test
    public void subQueryTest() {
        List<Object[]> list = orderRepository.subQueryTest();

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));

            System.out.println("order 아이디 : " + (Long) objects[0]);
            System.out.println("order 상태 : " + (OrderStatus) objects[1]);
            System.out.println("주문 개수 : " + (Long) objects[2]);
        }
    }
}
