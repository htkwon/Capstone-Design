package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
