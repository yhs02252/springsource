package com.example.memo.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.stereotype.Service;

import com.example.memo.dto.MemoDTO;
import com.example.memo.entity.Memo;
import com.example.memo.repository.MemoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    @Override
    public Long create(MemoDTO dto) {
        // Memo memo = Memo.builder()
        // .memoText(dto.getMemoText())
        // .build();
        // ↓ ↓ 똑같은 과정이나 다름없음

        Memo memo = memoDTOToEntity(dto);
        return memoRepository.save(memo).getMno();
    }

    @Override
    public MemoDTO read(Long mno) {
        Memo memo = memoRepository.findById(mno).get();

        return entityToMemoDTO(memo);
    }

    @Override
    public List<MemoDTO> list() {
        List<Memo> list = memoRepository.findAll();

        return list.stream().map(memo -> entityToMemoDTO(memo)).collect(Collectors.toList());
    }

    @Override
    public Long update(MemoDTO dto) {
        // Memo memo = memoRepository.findById(7L).get();
        // memo.setMemoText("ReWritedTest7");
        Memo memo = memoDTOToEntity(dto);
        return memoRepository.save(memo).getMno();
    }

    @Override
    public void deleteRow(Long mno) {
        memoRepository.deleteById(mno);
    }

}
