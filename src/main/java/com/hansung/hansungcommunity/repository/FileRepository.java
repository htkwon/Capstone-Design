package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface FileRepository extends
        JpaRepository<FileEntity,Long>,
        QuerydslPredicateExecutor<FileEntity> {


}
