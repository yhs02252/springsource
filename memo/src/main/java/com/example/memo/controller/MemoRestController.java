package com.example.memo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Log4j2
@RequiredArgsConstructor
@RestController // 화면단은 어떤 것이 들어와도 상관없음 -> 데이터 전달 유무 확인용
@RequestMapping("/rest")
public class MemoRestController {

    private final MemoService memoService;

    @GetMapping("/list")
    public List<MemoDTO> getList() {
        log.info("메모 리스트 요청");
        List<MemoDTO> list = memoService.list();
        return list;

    }

    // /rest/1
    @GetMapping("/{mno}")
    public MemoDTO getMno(@PathVariable Long mno) {
        log.info("메모 조회 {}", mno);

        MemoDTO dto = memoService.read(mno);
        return dto;
    }

    // @RequestBody : json => 객체
    @PostMapping("/create")
    public ResponseEntity<String> postCreate(@RequestBody MemoDTO dto) {
        log.info("메모 작성 {}", dto);
        Long mno = memoService.create(dto);

        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

    // rest 추가되는 methos : put(or patch) / DELETE

    @PutMapping("/{mno}")
    public ResponseEntity<String> putMno(@PathVariable Long mno, @RequestBody MemoDTO dto) {
        log.info("메모 수정 요청 {}", dto);

        dto.setMno(mno);
        memoService.update(dto);

        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

    // 메모삭제 : /memo/remove?mno1 get
    @DeleteMapping("/{mno}")
    public ResponseEntity<String> deleteMno(@PathVariable Long mno) {
        log.info("메모 삭제 요청 {}", mno);

        memoService.deleteRow(mno);

        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

}
