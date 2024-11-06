package com.example.memo.service;

import java.util.List;

import com.example.memo.dto.MemoDTO;
import com.example.memo.entity.Memo;

public interface MemoService {
    // CRUD 메소드
    Long create(MemoDTO dto);

    MemoDTO read(Long id);

    List<MemoDTO> list();

    Long update(MemoDTO dto);

    void deleteRow(Long id);

    // dto ==> entity 메소드
    public default Memo memoDTOToEntity(MemoDTO dto) {
        return Memo.builder()
                .mno(dto.getMno())
                .memoText(dto.getMemoText())
                .build();
    }

    // entity ==> dto 메소드
    public default MemoDTO entityToMemoDTO(Memo memo) {
        return MemoDTO.builder()
                .mno(memo.getMno())
                .memoText(memo.getMemoText())
                .createdDateTime(memo.getCreatedDateTime())
                .lastModifiedDateTime(memo.getLastModifiedDateTime())
                .build();
    }

}
