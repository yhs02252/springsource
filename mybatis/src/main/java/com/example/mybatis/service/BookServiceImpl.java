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
    public boolean create(BookDTO dto) {
        return bookMapper.create(dto) == 1 ? true : false;
    }

    @Override
    public BookDTO read(Long id) {
        return bookMapper.read(id);
    }

    @Override
    public boolean delete(Long id) {
        return bookMapper.delete(id) == 1 ? true : false;
    }

    @Override
    public boolean updatePrice(BookDTO dto) {
        return bookMapper.update(dto) == 1 ? true : false;
    }

    @Override
    public List<CategoryDTO> getCateList() {
        return bookMapper.categories();
    }

    @Override
    public List<PublisherDTO> getPublList() {
        return bookMapper.publishers();
    }

    @Override
    public List<BookDTO> getList(PageRequestDTO pageRequestDTO) {
        return bookMapper.listAll(pageRequestDTO);
    }

    @Override
    public int getTotalCnt(PageRequestDTO pageRequestDTO) {
        return bookMapper.totalCnt(pageRequestDTO);
    }

}
