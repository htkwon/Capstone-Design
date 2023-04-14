package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.recruit.RecruitBoardDetailDto;
import com.hansung.hansungcommunity.dto.recruit.RecruitBoardListDto;
import com.hansung.hansungcommunity.dto.recruit.RecruitBoardRequestDto;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.RecruitBoardRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<RecruitBoardListDto> getList(Pageable pageable) {
        Pageable setPage = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "createdAt");
        return recruitBoardRepository.findAll(setPage).getContent()
                .stream()
                .map(RecruitBoardListDto::from)
                .collect(Collectors.toList());
    }

    public RecruitBoardDetailDto getDetail(Long boardId) {
        RecruitBoard recruitBoard = recruitBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 구인 게시글이 없습니다."));

        return RecruitBoardDetailDto.from(recruitBoard);
    }

    /**
     * 조회수 증가 로직
     */
    @Transactional
    public void increaseHits(Long boardId) {
        RecruitBoard board = recruitBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("조회수 증가 실패, 해당하는 게시글이 없음"));

        board.increaseHits();
    }

}
