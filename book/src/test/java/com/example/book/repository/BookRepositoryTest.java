package com.example.book.repository;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    public void testCategoryInsert() {
        // 소셜, 건강, 컴퓨터, 여행, 경제
        Category category = Category.builder().name("소셜").build();
        categoryRepository.save(category);
        category = Category.builder().name("건강").build();
        categoryRepository.save(category);
        category = Category.builder().name("컴퓨터").build();
        categoryRepository.save(category);
        // category = Category.builder().id(5L).name("여행").build();
        // category = Category.builder().id(1L).name("경제").build();
    }

    @Test
    public void testPublisherInsert() {
        // 미래의창, 웅진리빙하우스, 김영사, 길벗, 문학과지성사
        Publisher publisher = Publisher.builder().name("미래의창").build();
        publisherRepository.save(publisher);
        publisher = Publisher.builder().name("웅진리빙하우스").build();
        publisherRepository.save(publisher);
        publisher = Publisher.builder().name("김영사").build();
        publisherRepository.save(publisher);
        publisher = Publisher.builder().name("길벗").build();
        publisherRepository.save(publisher);
        publisher = Publisher.builder().name("문학과지성사").build();
        publisherRepository.save(publisher);
    }

    @Test
    public void testBookInsert() {
        // 10권
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long num = (int) (Math.random() * 5) + 1;

            Book book = Book.builder()
                    .title("제목" + i)
                    .writer("저자" + i)
                    .price(15000 * i)
                    .salePrice((int) (15000 * i * 0.9))
                    .category(Category.builder().id(num).build())
                    .publisher(Publisher.builder().id(num).build())
                    .build();
            bookRepository.save(book);
        });
    }

    @Test
    @Transactional
    public void testList() {
        // 도서 목록 전체 조회
        bookRepository.findAll().forEach(books -> {
            System.out.println(books);
            // category 정보
            System.out.println(books.getCategory());
            // publisher 정보
            System.out.println(books.getPublisher());
        });
    }

    @Test
    @Transactional
    public void testGet() {
        // 특정 도서 조회
        Book book = bookRepository.findById(5L).get();
        System.out.println(book);
        System.out.println(book.getCategory().getName());
        System.out.println(book.getPublisher().getName());
    }

    @Test
    public void testUpdate() {
        Book book = bookRepository.findById(5L).get();
        book.setPrice(32000);
        book.setSalePrice(28000);
        bookRepository.save(book);
    }

    @Test
    public void testDelete() {
        bookRepository.deleteById(10L);
    }

    @Test
    public void testCategoryList() {
        // 카테고리 목록
        categoryRepository.findAll().forEach(cate -> {
            System.out.println(cate);
        });

        // 퍼블리셔 목록
        publisherRepository.findAll().forEach(publ -> {
            System.out.println(publ);
        });
    }

    @Test
    public void testPage() {
        // Pageable : 스프링 부트에서 제공하는 페이지 처리 객체

        // 1 page / 20개 최신 도서 정보
        // Pageable pageable = PageRequest.of(0, 0, Directuin.DESC)
        Pageable pageable = PageRequest.of(0, 20, Sort.by("id").descending());
        Page<Book> result = bookRepository.findAll(bookRepository.makePredicate(null, null), pageable);

        System.out.println("Total elements : " + result.getTotalElements());
        System.out.println("Total 페이지 : " + result.getTotalPages());
        result.getContent().forEach(book -> System.out.println(book));
    }

    @Test
    public void testSearchPage() {

        Pageable pageable = PageRequest.of(0, 20, Sort.by("id").descending());
        Page<Book> result = bookRepository.findAll(bookRepository.makePredicate("c", "건강"), pageable);

        System.out.println("Total elements : " + result.getTotalElements());
        System.out.println("Total 페이지 : " + result.getTotalPages());
        result.getContent().forEach(book -> System.out.println(book));
    }
}
