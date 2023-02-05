package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.QnaArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;


public interface QnaArticleRepository extends
        JpaRepository<QnaArticle,Long>,
        QuerydslPredicateExecutor<QnaArticle>{

    /*
    TODO: 추후 필요시 querydslBinderCustomizer 설정
     */


}
