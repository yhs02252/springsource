package com.example.movie.repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.Review;
import com.example.movie.entity.constant.MemberRole;

@SpringBootTest
public class MovieImageRepositoryTest {

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
    public void testListPage2() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Object[]> result = movieImageRepository.getTotalList(null, null, pageRequest);

        for (Object[] objects : result) {
            // System.out.println(Arrays.toString(objects)); // 한 페이지 전체 출력
            Movie movie = (Movie) objects[0];
            MovieImage movieImage = (MovieImage) objects[1];
            Long count = (Long) objects[2];
            Double avg = (Double) objects[3];

            System.out.println(movie);
            System.out.println(movieImage);
            System.out.println(count);
            System.out.println(avg);
        }
    }

    @Test
    public void testRow() {
        List<Object[]> result = movieImageRepository.getMovieRow(2L);

        System.out.println("Movie 정보 : " + result.get(0)[0]);

        for (Object[] objects : result) {
            // System.out.println(Arrays.toString(objects));

            // Movie movie = (Movie) objects[0];
            MovieImage movieImage = (MovieImage) objects[1];

            System.out.println("Movie 이미지 : " + movieImage);
        }
        System.out.println("Review 카운트 : " + result.get(0)[2]);
        System.out.println("Review 평점 : " + result.get(0)[3]);
    }
}
