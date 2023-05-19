package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.QnaBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface QnaBoardRepository extends
        JpaRepository<QnaBoard, Long>,
        QuerydslPredicateExecutor<QnaBoard> {

    /*
    TODO: 추후 필요시 querydslBinderCustomizer 설정
     */

    List<QnaBoard> findByCreatedAtAfter(LocalDateTime standardTime, Pageable pageable);

    @Query("SELECT q FROM QnaBoard q WHERE q.title LIKE %:search% OR q.content LIKE %:search%")
    Page<QnaBoard> findAllWithSearchParam(@Param("search") String search, Pageable pageable);

    @Query("SELECT q " +
            "FROM QnaBoard q LEFT OUTER JOIN q.bookmarks qb " +
            "GROUP BY q.id " +
            "ORDER BY COUNT(qb) DESC")
    Page<QnaBoard> findAllSortByBookmarks(Pageable setPage);

    @Query("SELECT q " +
            "FROM QnaBoard q LEFT OUTER JOIN q.bookmarks qb " +
            "WHERE q.title LIKE %:search% OR q.content LIKE %:search% " +
            "GROUP BY q.id " +
            "ORDER BY COUNT(qb) DESC")
    Page<QnaBoard> findAllSortByBookmarksWithSearchParam(String search, Pageable setPage);

}
