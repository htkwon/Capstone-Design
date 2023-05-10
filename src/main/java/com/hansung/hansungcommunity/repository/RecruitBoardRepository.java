package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.RecruitBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecruitBoardRepository extends JpaRepository<RecruitBoard, Long> {

    @Query("SELECT r FROM RecruitBoard r WHERE r.title LIKE %:search% OR r.content LIKE %:search%")
    Page<RecruitBoard> findAllWithSearchParam(String search, Pageable pageable);

}
