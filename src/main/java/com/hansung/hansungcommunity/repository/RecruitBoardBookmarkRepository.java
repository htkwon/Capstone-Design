package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.RecruitBoardBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface RecruitBoardBookmarkRepository extends JpaRepository<RecruitBoardBookmark,Long>,
        QuerydslPredicateExecutor<RecruitBoardBookmark> {


    Optional<List<RecruitBoardBookmark>> findAllByUserId(Long userId);
    Optional<RecruitBoardBookmark> findByRecruitBoardIdAndUserId(Long boardId, Long userId);

    Optional<List<RecruitBoardBookmark>> findAllByRecruitBoardId(Long boardId);

}
