package com.example.mart.repository;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mart.entitty.sports.Locker;
import com.example.mart.entitty.sports.Member;
import com.example.mart.repository.sports.LockerRepository;
import com.example.mart.repository.sports.SportsMemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class SportsRepositoryTest {

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private SportsMemberRepository sportsMemberRepository;

    @Test
    public void testLockerInsert() {
        // // locker 4
        // IntStream.rangeClosed(5, 8).forEach(i -> {
        // Locker locker = Locker.builder()
        // .name("락커" + i)
        // .build();
        // lockerRepository.save(locker);
        // });

        // member 4
        LongStream.rangeClosed(21, 24).forEach(i -> {

            Locker locker = Locker.builder().id(i).build();

            Member member = Member.builder()
                    .name("부원" + i)
                    .lockerList(locker)
                    .build();
            sportsMemberRepository.save(member);
        });
    }

    @Test
    public void testMemberUpdate() {
        Member sportsMember = sportsMemberRepository.findById(2L).get();
        sportsMember.setName("부원2test");
        sportsMemberRepository.save(sportsMember);
    }

    @Test
    @Transactional
    public void selectMemberTest() {
        // List<Member> memberlist = sportsMemberRepository.findAll();
        // System.out.println(memberlist);

        // for (Member member : memberlist) {
        // System.out.println(member);
        // }

        // sportsMemberRepository.findAll().forEach(members -> {
        // // 객체 그래프 탐색
        // System.out.println(members);
        // System.out.println(members.getLockerList());
        // System.out.println(members.getLockerList().getName());
        // });

        // Member sportsMember = sportsMemberRepository.findById(3L).get();
        // System.out.println(sportsMember);
        // System.out.println(sportsMember.getLockerList().getName());

        Member member = sportsMemberRepository.findById(22L).get();
        System.out.println(member);
        System.out.println(member.getLockerList());
    }

    // 양방향
    @Test
    public void membersLockerInsertTest() {
        // LongStream.rangeClosed(1, 4).forEach(i -> {
        // Member member = sportsMemberRepository.findById(i).get();
        // Locker locker = lockerRepository.findById(i).get();
        // locker.setSportsMember(member);
        // lockerRepository.save(locker);
        // });

        LongStream.rangeClosed(1, 4).forEach(i -> {
            Member member = sportsMemberRepository.findById(i).get();
            Locker locker = lockerRepository.findById(i).get();
            member.setLockerList(locker);
            sportsMemberRepository.save(member);
        });
    }

    @Test
    public void memberLockerDeleteTest() {
        // LongStream.rangeClosed(1, 4).forEach(i -> {
        Member member = sportsMemberRepository.findById(4L).get();
        member.setLockerList(null);
        sportsMemberRepository.save(member);
        // });
        // LongStream.rangeClosed(1, 4).forEach(i -> {
        // Locker locker = lockerRepository.findById(i).get();
        // locker.setSportsMember(null);
        // lockerRepository.save(locker);
        // });
    }

    @Test
    public void selectLockerTest() {

        lockerRepository.findAll().forEach(lockers -> {
            System.out.println(lockers);
            System.out.println(lockers.getSportsMember());
        });
    }
}
