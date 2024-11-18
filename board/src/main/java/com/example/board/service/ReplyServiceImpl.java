package com.example.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.board.dto.ReplyDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Reply;
import com.example.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO replyDTO) {
        return null;
    }

    @Override
    public List<ReplyDTO> list(Long bno) {
        Board board = Board.builder().bno(bno).build();
        List<Reply> list = replyRepository.findByBoardOrderByRno(board);

        return list.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());
    }

    @Override
    public ReplyDTO read(Long rno) {
        return null;
    }

    @Override
    public Long modify(ReplyDTO replyDTO) {
        return null;
    }

    @Override
    public void remove(Long rno) {
    }

}
