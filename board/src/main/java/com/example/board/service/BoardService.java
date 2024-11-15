package com.example.board.service;

import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;

@Service
public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO requestDTO);

    BoardDTO read(Long bno);

    Long update(BoardDTO dto);

    void remove(Long bno);

    // entity -> dto
    public default BoardDTO entityToDto(Board board, Member member, Long replyCnt) {
        return BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .regDate(board.getCreatedDateTime())
                .updateDate(board.getLastModifiedDateTime())
                .replyCnt(replyCnt)
                .build();
    }

    // dto -> entity
    public default Board dtoToEntity(BoardDTO dto) {
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        return Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

    }
}