package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.FreeReply;
import com.hansung.hansungcommunity.entity.QnaReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QnaReplyRepository extends JpaRepository<QnaReply, Long>,
        QuerydslPredicateExecutor<QnaReply> {

    @Query("SELECT f FROM QnaReply f WHERE f.board.id = :boardId")
    List<QnaReply> findAllByBoardId(@Param("boardId") Long boardId);

    Optional<List<FreeReply>> findAllByUserId(Long userId);
}
