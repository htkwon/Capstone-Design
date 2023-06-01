package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.Summary;
import com.hansung.hansungcommunity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SummaryRepository extends JpaRepository<Summary, Long> {

    List<Summary> findAllByUser(User user);

    List<Summary> findAllByUserIdAndIsFixedTrueOrderByCreatedAtDesc(Long userId);

}
