package com.example.movie.repository;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.Review;
import com.example.movie.entity.constant.MemberRole;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void testMovieInsert() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Movie movie = Movie.builder().title("Movie" + i).build();
            movieRepository.save(movie);

            int random = (int) (Math.random() * 5) + 1;

            for (int j = 0; j < random; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .imgName("test" + j + ".jpg")
                        .movie(movie)
                        .build();
                movieImageRepository.save(movieImage);
            }
        });
    }

    @Test
    public void testMemberInsert() {

        IntStream.rangeClosed(1, 50).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@naver.com")
                    .password(passwordEncoder.encode("1111"))
                    .nickName("nickName" + i)
                    .memberRole(MemberRole.MEMBER)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void testMovieReviewInsert() {

        IntStream.rangeClosed(1, 200).forEach(i -> {

            long random = (long) (Math.random() * 50) + 1;
            long random2 = (long) (Math.random() * 50) + 1;
            int grade = (int) (Math.random() * 5) + 1;
            Movie movie = movieRepository.findById(random).get();
            Member member = memberRepository.findById(random2).get();

            Review review = Review.builder()
                    .grade(grade)
                    .text("review" + i)
                    .movie(movie)
                    .member(member)
                    .build();
            reviewRepository.save(review);
        });
    }

    @Test
    public void testListPage() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getListPage(pageRequest);

        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void testListPage2() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Object[]> result = movieImageRepository.getTotalList(null, null, pageRequest);

        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    @Transactional
    public void testRemove() {
        // movieRepository.deleteById(50L);
        // 무결성 제약조건(C##MOVIEUSER.FKNIAHH54OB7EQVII88B71D0RP7)이 위배되었습니다- 자식 레코드가 발견되었습니다
        Movie movie = Movie.builder().mno(50L).build();

        movieImageRepository.deleteByMovie(movie);
        reviewRepository.deleteByMovie(movie);
        movieRepository.delete(movie);
    }

    @Commit
    @Test
    @Transactional
    public void testRemove2() {
        Movie movie = movieRepository.findById(49L).get();

        movieRepository.delete(movie);
    }
}
