package com.example.project2.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project2.entity.ProBoard;

public interface BoardRepository extends JpaRepository<ProBoard, Long> {

    // 1. spring data jpa 쿼리 메소드 방법 사용

    // where title = ?
    // List<ProBoard> findByTitle(String title);

    // where title like ?
    // List<ProBoard> findByTitleLike(String title);

    // where title like 'title'
    // List<ProBoard> findByTitleStartingWith(String title);

    // where writer like 'writer%'
    // List<ProBoard> findByWriterEndingWith(String writer);

    // where writer like '%writer%'
    // List<ProBoard> findByWriterContaining(String writer);

    // where writer like '%writer%' or title like '%title%'
    // List<ProBoard> findByWriterContainingOrTitleContaining(String writer, String
    // title);

    // where title like '%title%' and id > 10
    // List<ProBoard> findByTitleContainingAndIdGreaterThan(String title, Long id);

    // where id > 10 order by id desc
    // List<ProBoard> findByIdGreaterThanOrderByIdDesc(Long id);

    // List<ProBoard> findByIdGreaterThanOrderByIdDesc(Long id, Pageable pageable);

    // 2. @Query 어노테이션 사용

    // select * from proboar b where b.writer like '%user%' and b.id > 0 order by
    // b.id desc
    // @Query("SELECT b FROM ProBoard b WHERE b.writer LIKE %:writer% AND b.id > 0
    // ORDER BY b.id DESC")
    // List<ProBoard> findByWriterList(String writer);
    @Query("SELECT b FROM ProBoard b WHERE b.writer LIKE %?1% AND b.id > 0 ORDER BY b.id DESC")
    List<ProBoard> findByWriterList(String writer);

    // @Query("SELECT b FROM ProBoard b WHERE b.title = :title")
    // @Query("SELECT b FROM ProBoard b WHERE b.title LIKE %:title%")
    @Query("SELECT b FROM ProBoard b WHERE b.title LIKE %?1%")
    List<ProBoard> findByTitle(String title);

    @Query("SELECT b FROM ProBoard b WHERE b.title = :title AND b.writer = :writer ORDER BY b.id DESC")
    List<ProBoard> findByTitleAndWriterOrderByIdDesc(String title, String writer);

    @Query("SELECT b FROM ProBoard b WHERE b.writer LIKE %?1% OR b.title LIKE %?2%")
    List<ProBoard> findByTitleAndWriterLike(String writer, String title);

    @Query("SELECT b FROM ProBoard b WHERE b.writer LIKE %?1% OR b.title LIKE %?2% ORDER BY b.id DESC")
    List<ProBoard> findByTitleAndWriterLikeOrderByIdDesc(String writer, String title);
}
