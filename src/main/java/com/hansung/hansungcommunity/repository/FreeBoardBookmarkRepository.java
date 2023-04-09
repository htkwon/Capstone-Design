package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.FreeBoardBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import java.util.List;
import java.util.Optional;

public interface FreeBoardBookmarkRepository extends JpaRepository<FreeBoardBookmark, Long>,
        QuerydslPredicateExecutor<FreeBoardBookmark> {

    Optional<List<FreeBoardBookmark>> findAllByUserId(Long userId);
    Optional<FreeBoardBookmark> findByFreeBoardIdAndUserId(Long boardId, Long userId);

    Optional<List<FreeBoardBookmark>> findAllByFreeBoardId(Long boardId);
}
