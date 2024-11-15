package com.example.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void serviceListTest() {

        PageRequestDTO requestDTO = new PageRequestDTO();
        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(requestDTO);

        result.getDtoList().forEach(dto -> System.out.println(dto));
        System.out.println("전체 페이지" + result.getTotalPage());
    }
}
