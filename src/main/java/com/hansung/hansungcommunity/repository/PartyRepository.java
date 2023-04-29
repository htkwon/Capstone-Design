package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findByUserIdAndRecruitBoardId(Long userId, Long boardId);

}
