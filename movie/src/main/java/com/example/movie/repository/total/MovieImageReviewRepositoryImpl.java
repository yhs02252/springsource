package com.example.movie.repository.total;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.QMovie;
import com.example.movie.entity.QMovieImage;
import com.example.movie.entity.QReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MovieImageReviewRepositoryImpl extends QuerydslRepositorySupport implements MovieImageReviewRepository {

    public MovieImageReviewRepositoryImpl() {
        super(MovieImage.class);
    }

    @Override
    public Page<Object[]> getTotalList(String type, String keyword, Pageable pageable) {

        QMovieImage movieImage = QMovieImage.movieImage;
        QReview review = QReview.review;
        QMovie movie = QMovie.movie;

        JPQLQuery<MovieImage> query = from(movieImage).leftJoin(movie).on(movie.eq(movieImage.movie));

        // review 개수, review 평점 평균 쿼리문
        JPQLQuery<Long> rCnt = JPAExpressions.select(review.countDistinct()).from(review)
                .where(review.movie.eq(movieImage.movie));
        JPQLQuery<Double> rAvg = JPAExpressions.select(review.grade.avg().round()).from(review)
                .where(review.movie.eq(movieImage.movie));

        // movie image 정보 출력 범위 설정 쿼리문
        JPQLQuery<Long> inum = JPAExpressions.select(movieImage.inum.max()).from(movieImage).groupBy(movieImage.movie);

        JPQLQuery<Tuple> tuple = query.select(movie, movieImage, rCnt, rAvg)
                .where(movieImage.inum.in(inum));

        // id 가 0보다 클 경우 출력
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(movie.mno.gt(0L));

        // 검색

        tuple.where(builder);

        // Sort
        Sort sort = pageable.getSort();
        sort.stream().forEach(
                order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC; // 방향 정하기

                    String prop = order.getProperty(); // Sort 기준 컬럼명 가져오기

                    // order by 를 어느 entity에 적용할 지 결정
                    PathBuilder<Movie> orderByExpression = new PathBuilder<>(Movie.class, "movie");
                    tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
                });

        tuple.offset(pageable.getOffset()); // 가져올 행 결정
        tuple.limit(pageable.getPageSize()); // 가져올 행 숫자 결정

        System.out.println("=======쿼리문 확인======");
        System.out.println(tuple);
        System.out.println("========================");

        // Tuple 타입을 리스트로 변환
        List<Tuple> result = tuple.fetch();

        // 페이지 내 전체 행 개수
        Long count = tuple.fetchCount();

        return new PageImpl<>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
    }

    @Override
    public List<Object[]> getMovieRow(Long mno) {

        QMovieImage movieImage = QMovieImage.movieImage;
        QReview review = QReview.review;
        QMovie movie = QMovie.movie;

        JPQLQuery<MovieImage> query = from(movieImage).leftJoin(movie).on(movie.eq(movieImage.movie));

        // review 개수, review 평점 평균 쿼리문
        JPQLQuery<Long> rCnt = JPAExpressions.select(review.countDistinct()).from(review)
                .where(review.movie.eq(movieImage.movie));
        JPQLQuery<Double> rAvg = JPAExpressions.select(review.grade.avg().round()).from(review)
                .where(review.movie.eq(movieImage.movie));

        JPQLQuery<Tuple> tuple = query.select(movie, movieImage, rCnt, rAvg)
                .where(movieImage.movie.mno.eq(mno))
                .orderBy(movieImage.inum.desc());

        List<Tuple> result = tuple.fetch();
        return result.stream().map(t -> t.toArray()).collect(Collectors.toList());
    }
}
