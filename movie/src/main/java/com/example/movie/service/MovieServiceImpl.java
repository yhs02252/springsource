package com.example.movie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.movie.dto.MovieDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.dto.PageResultDTO;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.MovieImageRepository;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieImageRepository movieImageRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = movieImageRepository.getTotalList(pageRequestDTO.getType(), pageRequestDTO.getKeyword(),
                pageable);

        Function<Object[], MovieDTO> function = (en -> entityToDto((Movie) en[0],
                (List<MovieImage>) Arrays.asList((MovieImage) en[1]), (Long) en[2], (Double) en[3]));

        /*
         * List<String> lst = Arrays.asList(strs); // new ArrayList<String>(); 대신에 사용
         * System.out.println(lst); // [alpha, beta, charlie]
         * 
         * lst.add("ttt"); // <= error : asList()로 List를 생성하면 원소를 새롭게 추가할 수 없음
         * 
         * 
         * List<String> lst = new ArrayList<String>(Arrays.asList(strs));
         * System.out.println(lst); // [alpha, beta, charlie]
         * 
         * lst.add("ttt"); // 이제는 에러가 나지 않고 데이터를 추가 시킬 수 있다.
         * 
         * 
         * List는 내부 구조가 배열로 만들어져 있다. 따라서 asList()를 사용해서 반환되는 List도
         * 배열을 갖게 된다.
         * 
         * 이때, asList()를 사용해서 List 객체를 만들 때 새로운 배열 객체를 만드는 것이 아니라,
         * 원본 배열의 주소값을 가져오게 된다.
         * 
         * 따라서 asList()를 사용해서 내용을 수정하면 원본 배열도 함께 바뀌게 되고
         * 원본 배열을 수정하면 그 배열로 만들어뒀던 asList()를 이용한 List 내용도 바뀌게 된다.
         * 
         * 이러한 이유 때문에 Arrays.asList()로 만든 List에 새로운 원소를 추가하거나 삭제 할 수 없다.
         * 
         * Arrays.asList()는 배열의 내용을 수정하려고 할 때 List로 바꿔서 편리하게 사용하기 위함.
         * 
         * 여러개 또는 하나의 값이 들어올때 각각의 요소들을 배열처리 해주는 기능 가능
         */

        return new PageResultDTO<>(result, function);
    }

    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String, Object> entityMap = dtoToEntity(movieDTO);

        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImages = (List<MovieImage>) entityMap.get("movieImages");

        movieRepository.save(movie);

        if (movieImages.size() > 0) {
            movieImages.forEach(images -> movieImageRepository.save(images));
        }

        return movie.getMno();
    }

    @Override
    @Transactional
    public Long modify(MovieDTO movieDTO) {
        Map<String, Object> entityMap = dtoToEntity(movieDTO);

        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImages = (List<MovieImage>) entityMap.get("movieImages");

        movieRepository.save(movie);

        // 기존의 영화 이미지 제거
        movieImageRepository.deleteByMovie(movie);

        movieImages.forEach(movieImage -> {
            movieImageRepository.save(movieImage);
        });

        return movie.getMno();
    }

    @Override
    @Transactional
    public void delete(Long mno) {

        Movie movie = Movie.builder().mno(mno).build();

        movieImageRepository.deleteByMovie(movie);
        reviewRepository.deleteByMovie(movie);
        movieRepository.delete(movie);
    }

    @Override
    public MovieDTO get(Long mno) {
        List<Object[]> result = movieImageRepository.getMovieRow(mno);

        Movie movie = (Movie) result.get(0)[0];

        List<MovieImage> movieImages = new ArrayList<>();
        result.forEach(row -> {
            MovieImage movieImage = (MovieImage) row[1];
            movieImages.add(movieImage);
        });

        Long count = (Long) result.get(0)[2];
        Double avg = (Double) result.get(0)[3];

        return entityToDto(movie, movieImages, count, avg);
    }

}
