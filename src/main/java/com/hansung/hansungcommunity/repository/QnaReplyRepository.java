package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.QnaReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QnaReplyRepository extends JpaRepository<QnaReply, Long>,
        QuerydslPredicateExecutor<QnaReply> {
}
