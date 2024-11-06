package com.example.memo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequestMapping("/memo")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    // 메모작성 : /memo/create : get, post
    @GetMapping("/create")
    public void getCreateForm(MemoDTO dto) {
        log.info("메모 작성 폼 요청");
    }

    @PostMapping("/create")
    public String postCreate(@Valid MemoDTO dto, BindingResult result, RedirectAttributes rttr) {
        log.info("메모 입력 요청 {}", dto);

        // 유효성 검증
        if (result.hasErrors()) {
            return "/memo/create";
        }

        Long mno = memoService.create(dto);
        rttr.addFlashAttribute("msg", mno);
        return "redirect:list";
    }

    // 전체메모 : /memo/list :get
    // dispatcher servlet + mapping
    // ->
    @GetMapping("/list")
    public void getList(Model model) {
        log.info("메모 리스트 요청");
        List<MemoDTO> list = memoService.list();
        model.addAttribute("list", list);
    }

    // 메모수정(메모조회 + 수정) : get - /memo/read?mno=1, post - /memo/update?mno=1
    @GetMapping(path = { "/read", "/update" })
    public void getUpdate(@RequestParam Long mno, Model model) {
        log.info("메모 조회 {}", mno);
        MemoDTO dto = memoService.read(mno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/update")
    public String postUpdate(MemoDTO dto, RedirectAttributes rttr) {
        log.info("메모 수정 요청 {}", dto);

        Long mno = memoService.update(dto); // update 메소드에서 mno를 받환해줌

        rttr.addFlashAttribute("msg", mno + "번 메모가 삭제되었습니다");

        return "redirect:list";
    }

    // 메모삭제 : /memo/remove?mno1 get
    @GetMapping("/remove")
    public String getDelete(@RequestParam Long mno, RedirectAttributes rttr) {
        log.info("메모 삭제 요청 {}", mno);

        memoService.deleteRow(mno);

        rttr.addFlashAttribute("msg", mno + "번 메모가 삭제되었습니다");

        return "redirect:list";
    }

}
