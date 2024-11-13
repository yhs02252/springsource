package com.example.guestbook.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.guestbook.dto.GuestBookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.repository.GuestBookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookRepository guestBookRepository;

    @Override
    public Long register(GuestBookDTO dto) {
        return null;
    }

    @Override
    public GuestBookDTO read(Long gno) {
        return null;
    }

    // 기존 방식
    // @Override
    // public List<GuestBookDTO> getList() {
    // List<GuestBook> result = guestBookRepository.findAll();
    // return result.stream().map(entity -> entityToDto(entity))
    // .collect(Collectors.toList());
    // }

    @Override
    public PageResultDTO<GuestBookDTO, GuestBook> list(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        // 하나의 페이지에 보여지게 만들 구문
        Page<GuestBook> result = guestBookRepository
                .findAll(guestBookRepository.makePredicate(requestDTO.getType(), requestDTO.getKeyword()), pageable);

        // 요소가 들어왔을때 실행될 동작 설정
        Function<GuestBook, GuestBookDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public Long update(GuestBookDTO dto) {
        return null;
    }

    @Override
    public void delete(Long gno) {

    }

}
