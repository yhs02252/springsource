package com.example.mybatis.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Data;

// page<book> result 결과를 담는 DTO
// Entity ==> Dto : result.getContent() ==> List<~~DTO>로 변경하는 작업 필요

@Data
public class PageResultDTO<DTO> { // 일반 function 처럼 전달자 지정 -> 다른 객체가 들어올수 있음

    // 화면에 보여줄 DTO 리스트
    private List<DTO> dtoList;

    // 총 페이지 번호
    private int totalPage;

    // 총 개수
    private int total;
    private PageRequestDTO requestDTO;

    // 시작 페이지, 끝 페이지 번호
    private int start, end;

    // 이전, 다음 여부
    private boolean prev, next;

    // 화면에 보여줄 페이지 번호 목록
    private List<Integer> pageList;

    // 페이지 result 생성자
    public PageResultDTO(PageRequestDTO requestDTO, int total, List<DTO> dtoList) {

        this.total = total;
        this.dtoList = dtoList;

        int tempEnd = (int) (Math.ceil(requestDTO.getPage() / 10.0) * requestDTO.getSize());

        totalPage = (int) (Math.ceil((total / 1.0) / requestDTO.getSize()));

        this.start = tempEnd - 9; // 페이지 바에서 tempEnd 로 도출한 전체 페이지 수에서 시작부분 결정
        this.prev = this.start > 1; // 페이지 바에서 start가 1보다 크다면 보여지도록 설정
        this.end = totalPage > tempEnd ? tempEnd : totalPage; // 페이지 바에서 tempEnd 로 도출한 전체 페이지 수에 따라 마지막 페이지 숫자 결정
        this.next = totalPage > tempEnd; // 페이지 바에서 전체 페이지가 tempEnd 보다 작다면 보여지도록 설정

        // IntStream.rangeClosed(start, end) : 현재 Int 상태
        // .boxed = wrapper 타입으로 변환시켜줌
        pageList = IntStream.rangeClosed(start, end)
                .boxed()
                .collect(Collectors.toList());
    }

}
