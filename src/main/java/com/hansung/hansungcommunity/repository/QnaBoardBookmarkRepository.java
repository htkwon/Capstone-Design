package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.QnaBoardBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;


public interface QnaBoardBookmarkRepository extends JpaRepository<QnaBoardBookmark, Long>,
        QuerydslPredicateExecutor<QnaBoardBookmark> {

    List<QnaBoardBookmark> findAllByUserId(Long userId);

    Optional<QnaBoardBookmark> findByQnaBoardIdAndUserId(Long boardId, Long userId);

    List<QnaBoardBookmark> findAllByQnaBoardId(Long boardId);

}
