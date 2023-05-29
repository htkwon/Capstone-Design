package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.NoticeReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeReplyRepository extends JpaRepository<NoticeReply, Long>,
        QuerydslPredicateExecutor<NoticeReply> {

    @Query("SELECT DISTINCT n FROM NoticeReply n LEFT JOIN FETCH n.children WHERE n.noticeBoard.id = :articleId ORDER BY n.id ASC")
    List<NoticeReply> findAllWithChildrenByArticleId(@Param("articleId") Long articleId);

    List<NoticeReply> findAllByParentId(Long id);

}
