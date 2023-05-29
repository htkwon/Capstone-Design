package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByBoardIdAndUserId(Long boardId, Long userId);

    long countByBoardId(Long boardId);

    boolean existsByBoardIdAndUserId(Long boardId, Long userId);

    List<Bookmark> findAllByUserId(Long userId);

}
