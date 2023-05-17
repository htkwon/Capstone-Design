package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeBoard, Long>,
        QuerydslPredicateExecutor<NoticeBoard> {

    List<NoticeBoard> findAllById(Long id);

}
