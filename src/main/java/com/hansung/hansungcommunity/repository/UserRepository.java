package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Long>,
        QuerydslPredicateExecutor<QnaBoard> {

    boolean existsUserByStudentId(String studentId);

}
