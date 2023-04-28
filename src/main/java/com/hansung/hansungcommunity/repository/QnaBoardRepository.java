package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.QnaBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;
import java.util.List;


public interface QnaBoardRepository extends
        JpaRepository<QnaBoard, Long>,
        QuerydslPredicateExecutor<QnaBoard> {

    /*
    TODO: 추후 필요시 querydslBinderCustomizer 설정
     */

    List<QnaBoard> findByCreatedAtAfter(LocalDateTime standardTime, Pageable pageable);

}
