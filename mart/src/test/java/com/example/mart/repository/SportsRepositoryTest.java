package com.example.mart.repository;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mart.entitty.sports.Locker;
import com.example.mart.entitty.sports.Member;
import com.example.mart.repository.sports.LockerRepository;
import com.example.mart.repository.sports.SportsMemberRepository;

@SpringBootTest
public class SportsRepositoryTest {

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private SportsMemberRepository sportsMemberRepository;

    @Test
    public void testLockerInsert() {
        // locker 4
        IntStream.rangeClosed(1, 4).forEach(i -> {
            Locker locker = Locker.builder()
                    .name("락커" + i)
                    .build();
            lockerRepository.save(locker);
        });

        // member 4
        LongStream.rangeClosed(1, 4).forEach(i -> {

            Locker locker = Locker.builder().id(i).build();

            Member member = Member.builder()
                    .name("부원" + i)
                    .lockerList(locker)
                    .build();
            sportsMemberRepository.save(member);
        });
    }

}
