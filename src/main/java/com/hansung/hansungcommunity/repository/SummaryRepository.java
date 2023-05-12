package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.Summary;
import com.hansung.hansungcommunity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface SummaryRepository extends JpaRepository<Summary, Long>,
        QuerydslPredicateExecutor<User> {

    List<Summary> findAllByUser(User user);

}
