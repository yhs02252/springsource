package com.example.guestbook.service;

import org.springframework.stereotype.Service;

import com.example.guestbook.dto.GuestBookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.GuestBook;

@Service
public interface GuestBookService {

    // 등록
    Long register(GuestBookDTO dto);

    // 조회
    GuestBookDTO read(Long gno);

    // 전체 조회
    // List<GuestBookDTO> getList(); // 기존 방식
    PageResultDTO<GuestBookDTO, GuestBook> list(PageRequestDTO requestDTO);

    // 수정
    Long update(GuestBookDTO dto);

    // 삭제
    void delete(Long gno);

    public default GuestBookDTO entityToDto(GuestBook entity) {
        return GuestBookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .writer(entity.getWriter())
                .content(entity.getContent())
                .createdDateTime(entity.getCreatedDateTime())
                .lastModifiedDateTime(entity.getLastModifiedDateTime())
                .build();
    }

    public default GuestBook dtoToEntity(GuestBookDTO guestBookDTO) {
        return GuestBook.builder()
                .gno(guestBookDTO.getGno())
                .title(guestBookDTO.getTitle())
                .writer(guestBookDTO.getWriter())
                .content(guestBookDTO.getContent())
                .build();
    }
}
