package com.example.movie.service;

import java.util.List;
import java.util.Map;
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
                .reviewAvg(reviewAvg)
                .reviewCnt(reviewCnt)
                .regDate(movie.getCreatedDateTime())
                .upDateTime(movie.getLastModifiedDateTime())
                .build();

        // MovieImage => MovieImageDTO 변경 후 리스트 작업
        List<MovieImageDTO> movieImageDTOs = movieimages.stream().map(movieimage -> {
            return MovieImageDTO.builder()
                    .inum(movieimage.getInum())
                    .uuid(movieimage.getUuid())
                    .imgName(movieimage.getImgName())
                    .path(movieimage.getPath())
                    .build();
        }).collect(Collectors.toList());

        movieDTO.setMovieImageDTOs(movieImageDTOs);

        return movieDTO;
    }

    default Map<String, Object> dtoToEntity(MovieDTO dto) {
        return null;
    }
}
