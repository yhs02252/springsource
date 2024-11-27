package com.example.movie.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.movie.dto.MovieDTO;
import com.example.movie.dto.MovieImageDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.dto.PageResultDTO;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;

@Service
public interface MovieService {

    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    Long register(MovieDTO movieDTO);

    Long modify(MovieDTO movieDTO);

    void delete(Long mno);

    MovieDTO get(Long mno);

    default MovieDTO entityToDto(Movie movie, List<MovieImage> movieimages, Long reviewCnt, Double reviewAvg) {

        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                // .movieImageDTOs(movieimage)
                .reviewAvg(reviewAvg != null ? reviewAvg : 0.0d)
                .reviewCnt(reviewCnt)
                .regDate(movie.getCreatedDateTime())
                .upDateTime(movie.getLastModifiedDateTime())
                .build();

        // ※ MovieImage => MovieImageDTO 변경 후 리스트 작업
        List<MovieImageDTO> movieImageDTOs = movieimages.stream().map(movieimage -> {
            return MovieImageDTO.builder()
                    .inum(movieimage.getInum())
                    .uuid(movieimage.getUuid())
                    .imgName(movieimage.getImgName())
                    .path(movieimage.getPath())
                    .regDate(movieimage.getCreatedDateTime())
                    .upDateTime(movieimage.getLastModifiedDateTime())
                    .build();
        }).collect(Collectors.toList());

        movieDTO.setMovieImageDTOs(movieImageDTOs);

        return movieDTO;
    }

    default Map<String, Object> dtoToEntity(MovieDTO dto) {
        Map<String, Object> resultMap = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(dto.getMno())
                .title(dto.getTitle())
                .build();
        resultMap.put("movie", movie);

        List<MovieImageDTO> movieImageDTOs = dto.getMovieImageDTOs();
        // ※ MovieImageDTO => MovieImage 변경 후 MovieImage List 형태로 작성

        // List<MovieImage> movieImages = new ArrayList<>();

        // if (movieImageDTOs != null && movieImageDTOs.size() > 0) {
        // movieImageDTOs.forEach(movieImageDTO -> {
        // MovieImage movieImage = MovieImage.builder()
        // .uuid(movieImageDTO.getUuid())
        // .imgName(movieImageDTO.getImgName())
        // .path(movieImageDTO.getPath())
        // .movie(movie)
        // .build();
        // movieImages.add(movieImage);
        // });
        // }

        if (movieImageDTOs != null && movieImageDTOs.size() > 0) {
            List<MovieImage> movieImages = movieImageDTOs.stream().map(movieImageDTO -> {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(movieImageDTO.getUuid())
                        .imgName(movieImageDTO.getImgName())
                        .path(movieImageDTO.getPath())
                        .movie(movie)
                        .build();
                return movieImage;
            }).collect(Collectors.toList());

            resultMap.put("movieImages", movieImages);
        }

        return resultMap;
    }
}
