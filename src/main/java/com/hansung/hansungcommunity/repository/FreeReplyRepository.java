package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.FreeReply;
import com.hansung.hansungcommunity.entity.QnaReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FreeReplyRepository extends JpaRepository<FreeReply, Long>,
        QuerydslPredicateExecutor<FreeReply> {


    @Query("SELECT DISTINCT r FROM FreeReply r LEFT JOIN FETCH r.children WHERE r.board.id = :articleId ORDER BY r.id ASC")
    List<FreeReply> findAllWithChildrenByArticleId(@Param("articleId") Long articleId);




}
