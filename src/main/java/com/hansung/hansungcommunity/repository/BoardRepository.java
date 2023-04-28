package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b WHERE b.createdAt >= :standardTime ORDER BY b.views DESC")
    List<Board> getPopularBoards(@Param("standardTime") LocalDateTime standardTime, Pageable pageable);

}
