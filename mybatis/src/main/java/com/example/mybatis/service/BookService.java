package com.example.mybatis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mybatis.dto.BookDTO;
import com.example.mybatis.dto.CategoryDTO;
import com.example.mybatis.dto.PageRequestDTO;
import com.example.mybatis.dto.PublisherDTO;

@Service
public interface BookService {

    Long create(BookDTO dto);

    BookDTO read(Long id);

    // List<BookDTO> getList();
    List<BookDTO> getList(PageRequestDTO pageRequestDTO);

    int getTotalCnt(PageRequestDTO requestDTO);

    Long updatePrice(BookDTO dto);

    void delete(Long id);

    List<CategoryDTO> getCateList();

    List<PublisherDTO> getPublList();

}
