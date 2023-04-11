package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.recruit.RecruitBoardRequestDto;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.RecruitBoardRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitBoardService {

    private final RecruitBoardRepository recruitBoardRepository;
    private final UserRepository userRepository;

    /**
     * 게시글 저장
     */
    @Transactional
    public Long post(Long userId, RecruitBoardRequestDto dto) {
        User user = userRepository.getReferenceById(userId);
        RecruitBoard saved = recruitBoardRepository.save(RecruitBoard.createBoard(dto, user));

        return saved.getId();
    }

}
