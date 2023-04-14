package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.FreeBoardBookmark;
import com.hansung.hansungcommunity.entity.QnaBoardBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface QnaBoardBookmarkRepository extends JpaRepository<QnaBoardBookmark, Long>,
        QuerydslPredicateExecutor<QnaBoardBookmark> {

    Optional<List<QnaBoardBookmark>> findAllByUserId(Long userId);
    Optional<QnaBoardBookmark> findByQnaBoardIdAndUserId(Long boardId, Long userId);

    Optional<List<QnaBoardBookmark>> findAllByQnaBoardId(Long boardId);



}
