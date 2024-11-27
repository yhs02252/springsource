package com.example.movie.dto;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {
    // uuid, fileName, folderPath

    private String uuid; // movieImage UUID

    private String fileName; // 원본 파일명

    private String folderPath; // 년월일 폴더

    // 썸네일 경로
    public String getThumbImageURL() {
        String fullPath = "";
        try {
            fullPath = URLEncoder.encode(folderPath + File.separator + "s_" + uuid + "_" + fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fullPath;
    }

    // 원본 이미지 경로
    public String getImageURL() {
        String fullPath = "";
        try {
            fullPath = URLEncoder.encode(folderPath + File.separator + uuid + "_" + fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fullPath;
    }
}
