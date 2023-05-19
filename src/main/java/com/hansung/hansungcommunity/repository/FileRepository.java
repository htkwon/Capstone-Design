package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.FileEntity;
import com.hansung.hansungcommunity.entity.FreeBoard;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;


public interface FileRepository extends
        JpaRepository<FileEntity, Long>,
        QuerydslPredicateExecutor<FileEntity> {

    List<FileEntity> findAllByFreeBoard(FreeBoard freeBoard);

    List<FileEntity> findAllByQnaBoard(QnaBoard qnaBoard);

    List<FileEntity> findAllByRecruitBoard(RecruitBoard recruitBoard);


}
