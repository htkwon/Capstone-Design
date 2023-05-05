package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findByUserIdAndRecruitBoardId(Long userId, Long boardId);

    List<Party> findByRecruitBoardId(Long boardId);

    Long countByRecruitBoardId(Long boardId);

    Long countByRecruitBoardIdAndIsApprovedTrue(Long boardId);

}
