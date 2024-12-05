package com.example.mybatis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mybatis.dto.BookDTO;
import com.example.mybatis.dto.CategoryDTO;
import com.example.mybatis.dto.PageRequestDTO;
import com.example.mybatis.dto.PublisherDTO;
import com.example.mybatis.mapper.BookMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    @Override
    public Long create(BookDTO dto) {
        return null;
    }

    @Override
    public BookDTO read(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public List<CategoryDTO> getCateList() {
        return null;
    }

    @Override
    public List<PublisherDTO> getPublList() {
        return null;
    }

    @Override
    public List<BookDTO> getList(PageRequestDTO pageRequestDTO) {
        return bookMapper.listAll(pageRequestDTO);
    }

    @Override
    public Long updatePrice(BookDTO dto) {
        return null;
    }

    @Override
    public int getTotalCnt(PageRequestDTO pageRequestDTO) {
        return bookMapper.totalCnt(pageRequestDTO);
    }

}
