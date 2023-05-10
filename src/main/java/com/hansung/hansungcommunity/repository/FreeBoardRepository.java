package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

    @Query("SELECT f FROM FreeBoard f WHERE f.title LIKE %:search% OR f.content LIKE %:search%")
    Page<FreeBoard> findAllWithSearchParam(@Param("search") String search, Pageable pageable);

}
