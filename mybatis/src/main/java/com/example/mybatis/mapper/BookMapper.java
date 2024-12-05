package com.example.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.mybatis.dto.BookDTO;
import com.example.mybatis.dto.PageRequestDTO;

@Mapper
public interface BookMapper {
    public BookDTO read(Long id);

    public List<BookDTO> listAll(PageRequestDTO requestDTO);

    public int totalCnt(PageRequestDTO requestDTO);
}