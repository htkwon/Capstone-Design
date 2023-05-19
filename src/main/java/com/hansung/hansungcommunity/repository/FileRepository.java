package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.*;
import net.bytebuddy.build.Plugin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.io.File;
import java.util.List;


public interface FileRepository extends
        JpaRepository<FileEntity, Long>,
        QuerydslPredicateExecutor<FileEntity> {

    List<FileEntity> findAllByFreeBoard(FreeBoard freeBoard);

    List<FileEntity> findAllByQnaBoard(QnaBoard qnaBoard);

    List<FileEntity> findAllByRecruitBoard(RecruitBoard recruitBoard);

    List<FileEntity> findAllByNoticeBoard(NoticeBoard noticeBoard);

}
