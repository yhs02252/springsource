package com.example.project2.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project2.entity.Item;
import com.example.project2.entity.constant.ItemStatus;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 10)
                .forEach(i -> {
                    Item item = Item.builder()
                            .itemNm("운동화" + i)
                            .price(95000)
                            .stockNumber(15)
                            .itemSellStatus(ItemStatus.SELL)
                            .regTime(LocalDateTime.now())
                            .build();
                    itemRepository.save(item);
                });
    }

    @Test
    public void selectOneTest() {
        // System.out.println(itemRepository.findById(5L).get());
        Item item = itemRepository.findById(5L).get();
        System.out.println(item);
    }

    @Test
    public void selectAllTest() {
        // Item item = itemRepository.findAll().get(i);
        itemRepository.findAll().forEach(item -> System.out.println(item));
    }

    @Test
    public void updateTest() {
        // IntStream.range(0, 10).forEach(i -> {
        // Item item = itemRepository.findAll().get(i); // get() <- 배열 기준 0 ~ N번 순으로
        // 생각하면됨
        // item.setItemNm("운동화" + (i + 1));
        // itemRepository.save(item);
        // });

        Item item = itemRepository.findById(6L).get();
        item.setPrice(85000);
        item.setUpdateTime(LocalDateTime.now());
        itemRepository.save(item);
    }

    @Test
    public void deleteTest() {
        itemRepository.deleteById(9L);

        // Optional<Item> result = itemRepository.findById(9L);
        // result.ifPresent(item -> {
        // itemRepository.delete(item);
        // });
    }
}
