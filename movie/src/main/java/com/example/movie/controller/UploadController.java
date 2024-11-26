package com.example.movie.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/upload")
@Log4j2
public class UploadController {

    @Value("${com.example.movie.upload.path}")
    private String uploadPath; // application.properties 에서 지정한 폴더 이름 => com.example.movie.upload.path = upload

    @GetMapping("/upload")
    public void getUpload() {
        log.info("업로드 폼 요청");
    }

    @PostMapping("/upload")
    public void postUpload(MultipartFile[] uploadFiles) {

        for (MultipartFile multipartFile : uploadFiles) {
            log.info("OriginalFilename : {}", multipartFile.getOriginalFilename());
            log.info("Name : {}", multipartFile.getName());
            log.info("Size : {}", multipartFile.getSize());
            log.info("ContentType: {}", multipartFile.getContentType());
            log.info("기본 : {}", multipartFile);

            // 이미지 파일 여부 확인
            if (!multipartFile.getContentType().startsWith("image")) {
                return;
            }

            // 사용자가 올린 파일명
            String originName = multipartFile.getOriginalFilename();

            // 파일 저장 코드 작성
            String saveFolderPath = makeFolder(); // YYYY/MM/dd
            System.out.println("파일 디렉토리 확인 : " + saveFolderPath);

            // UUID - 중복파일 처리
            String uuid = UUID.randomUUID().toString();
            // upload/2024/11/26/fdc0b7fa-f501-4e08-abfb-8808ff29415e_1.jpg
            String saveName = uploadPath + File.separator + saveFolderPath + File.separator + uuid + "_" + originName;

            Path savePath = Paths.get(saveName);

            try {
                multipartFile.transferTo(savePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private String makeFolder() {
        // 오늘날짜 구하기
        LocalDate today = LocalDate.now();
        log.info("오늘 날짜 - {}", today); // 2024-11-26

        // 날짜 형태(/ / /)로 폴더 생성을 위해 날짜 format 시키기
        String dateStr = today.format(DateTimeFormatter.ofPattern("YYYY/MM/dd"));

        // 부모(지정한 경로), 자식 폴더(포맷한 날짜) 이용하여 생성
        File dirs = new File(uploadPath, dateStr);
        if (!dirs.exists()) {
            dirs.mkdirs(); // 실제 폴더 생성(지정한 폴더의 모든 경로)
        }

        // 폴더구조 : / or \\
        // c:/upload/1.jpg or c:\\upload\\1.jpg

        // 날짜나 시간, 숫자 특정 형태 지정 => Formatter
        // SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
        // sdf.format(new Date());

        // 오늘날짜로 폴더 생성
        return dateStr;
    }
}
