package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.QnaReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface QnaReplyRepository extends JpaRepository<QnaReply, Long>,
        QuerydslPredicateExecutor<QnaReply> {

    QnaReply findFirstByBoardIdAndAdoptTrue(Long boardId);

    List<QnaReply> findByUserId(Long userId);

    List<QnaReply> findAllByParentId(Long id);

}
