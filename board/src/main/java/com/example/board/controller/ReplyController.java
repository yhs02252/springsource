package com.example.board.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.ReplyDTO;
import com.example.board.entity.Reply;
import com.example.board.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/replies")
@RestController
public class ReplyController {

    private final ReplyService service;

    @GetMapping("/board/{bno}")
    public ResponseEntity<List<ReplyDTO>> getPathList(@PathVariable Long bno) {
        log.info("댓글 요청 : {}", bno);
        List<ReplyDTO> replies = service.list(bno);

        return new ResponseEntity<>(replies, HttpStatus.OK);
    }

}
