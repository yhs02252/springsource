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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/replies")
@RestController
public class ReplyController {

    private final ReplyService service;

    @GetMapping("/board/{bno}")
    public ResponseEntity<List<ReplyDTO>> getReplyList(@PathVariable Long bno) {
        log.info("댓글 요청 : {}", bno);
        List<ReplyDTO> replies = service.list(bno);

        return new ResponseEntity<>(replies, HttpStatus.OK);
    }

    // 작성자 == 로그인 사용자
    @PreAuthorize("authentication.name == #replyDTO.replyerEmail")
    @PostMapping("/new")
    public ResponseEntity<Long> postRegister(@RequestBody ReplyDTO replyDTO) {
        log.info("댓글 작성 {}", replyDTO);

        Long rno = service.register(replyDTO);

        return new ResponseEntity<Long>(rno, HttpStatus.OK);
    }

    @GetMapping("/{rno}")
    public ResponseEntity<ReplyDTO> getReply(@PathVariable Long rno) {
        log.info("댓글 상세 조회 {}", rno);

        ReplyDTO replyDTO = service.read(rno);

        return new ResponseEntity<ReplyDTO>(replyDTO, HttpStatus.OK);
    }

    @PreAuthorize("authentication.name == #replyDTO.replyerEmail")
    @PutMapping("/{rno}")
    public ResponseEntity<Long> putMethodName(@PathVariable Long rno, @RequestBody ReplyDTO replyDTO) {
        log.info("댓글 수정 : {} , {}", rno, replyDTO);

        replyDTO.setRno(rno); // ReplyDTO 에서 가져온 댓글을 다시 찾기
        rno = service.modify(replyDTO); // 새로운 정보 담기 완료한 댓글 rno 를 넘기기
        return new ResponseEntity<Long>(rno, HttpStatus.OK);
    }

    @PreAuthorize("authentication.name == #replyDTO.replyerEmail")
    @DeleteMapping("/{rno}")
    public ResponseEntity<Long> deleteReply(@PathVariable Long rno, @RequestBody ReplyDTO replyDTO) {
        log.info("댓글 삭제 {}, {}", rno, replyDTO);

        service.remove(rno);

        return new ResponseEntity<Long>(rno, HttpStatus.OK);
    }
}
