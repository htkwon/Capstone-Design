package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findByUserIdAndRecruitBoardId(Long userId, Long boardId);

    List<Party> findByRecruitBoardId(Long boardId);

    List<Party> findByUserId(Long userId);

    Long countByRecruitBoardId(Long boardId);

    Long countByRecruitBoardIdAndIsApprovedTrue(Long boardId);

    long countByUserId(Long userId);

}
