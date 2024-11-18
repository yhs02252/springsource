package com.example.board.service;

import java.util.List;

import com.example.board.dto.ReplyDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Reply;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    List<ReplyDTO> list(Long bno);

    ReplyDTO read(Long rno);

    Long modify(ReplyDTO replyDTO);

    void remove(Long rno);

    public default Reply dtoToEntity(ReplyDTO replyDTO) {
        Board board = Board.builder().bno(replyDTO.getBno()).build();
        return Reply.builder()
                .rno(replyDTO.getRno())
                .replyer(replyDTO.getReplyer())
                .text(replyDTO.getText())
                .board(board)
                .build();
    }

    public default ReplyDTO entityToDto(Reply reply) {
        return ReplyDTO.builder()
                .rno(reply.getRno())
                .replyer(reply.getReplyer())
                .text(reply.getText())
                .bno(reply.getBoard().getBno())
                .regDate(reply.getCreatedDateTime())
                .updateDate(reply.getLastModifiedDateTime())
                .build();

    }
}
