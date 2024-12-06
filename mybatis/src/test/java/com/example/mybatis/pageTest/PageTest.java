package com.example.mybatis.pageTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mybatis.dto.BookDTO;
import com.example.mybatis.dto.PageRequestDTO;
import com.example.mybatis.dto.PageResultDTO;
import com.example.mybatis.service.BookService;

@SpringBootTest
public class PageTest {

    @Autowired
    private BookService service;

    @Test
    public void testPageList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(10)
                .size(10)
                .build();

        List<BookDTO> result = service.getList(pageRequestDTO);
        int total = service.getTotalCnt(pageRequestDTO);

        PageResultDTO<BookDTO> pageResultDTO = new PageResultDTO<>(pageRequestDTO, total, result);
        System.out.println("totalPage : " + pageResultDTO.getTotalPage());
        System.out.println("total : " + pageResultDTO.getTotal());
        System.out.println("start : " + pageResultDTO.getStart());
        System.out.println("end : " + pageResultDTO.getEnd());
    }

}
