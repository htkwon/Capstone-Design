package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.QUser;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>,
        QuerydslPredicateExecutor<QnaBoard> {

    boolean existsUserByStudentId(String studentId);

}
