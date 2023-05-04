package com.hansung.hansungcommunity.repository;


import com.hansung.hansungcommunity.entity.FreeReply;
import com.hansung.hansungcommunity.entity.RecruitReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecruitReplyRepository extends JpaRepository<RecruitReply,Long>,
        QuerydslPredicateExecutor<RecruitReply> {


    @Query("SELECT DISTINCT r FROM RecruitReply r LEFT JOIN FETCH r.children WHERE r.recruitBoard.id = :articleId ORDER BY r.id ASC")
    List<RecruitReply> findAllWithChildrenByArticleId(@Param("articleId") Long articleId);

    Optional<List<RecruitReply>> findAllByUserId(Long userId);
    Optional<RecruitReply> findByRecruitBoardId(Long userId);
}
