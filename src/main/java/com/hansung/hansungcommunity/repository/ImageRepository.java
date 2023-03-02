package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


public interface ImageRepository extends
        JpaRepository<Image,Long>,
        QuerydslPredicateExecutor<Image> {


}
