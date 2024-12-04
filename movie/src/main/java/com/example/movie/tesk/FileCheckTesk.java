package com.example.movie.tesk;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.movie.dto.MovieImageDTO;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.MovieImageRepository;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class FileCheckTesk {

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Value("${com.example.movie.upload.path}")
    private String uploadPath;

    private String getYesterdayFolder() {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String result = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return result.replace("-", File.separator);
    }

    // 스케쥴에 맞춰서 스프링에 메소드를 자동으로 호출해줌
    @Scheduled(cron = "0 * * * * * ")
    public void checkFile() {
        log.info("file check 메소드 실행");

        // db에서 전일자 이미지 파일 목록 추출
        List<MovieImage> oldMovieImages = movieImageRepository.findOldFileAll();

        // entity -> dto
        List<MovieImageDTO> movieImageDTOs = oldMovieImages.stream().map(movieImage -> {
            return MovieImageDTO.builder()
                    .inum(movieImage.getInum())
                    .uuid(movieImage.getUuid())
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .build();
        }).collect(Collectors.toList());

        // upload/2024/12/03/~~~~~_1.jpg
        // upload/2024/12/03/s_~~~~~~_1.jpg

        // 어제날짜의 파일 목록 추출

        // 비교후 db 목록과 일치하지 않는 파일 제거

    }
}
