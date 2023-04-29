package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.recruit.RecruitBoardApplyRequestDto;
import com.hansung.hansungcommunity.dto.recruit.RecruitBoardDetailDto;
import com.hansung.hansungcommunity.dto.recruit.RecruitBoardListDto;
import com.hansung.hansungcommunity.dto.recruit.RecruitBoardRequestDto;
import com.hansung.hansungcommunity.entity.Party;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.PartyRepository;
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
    private final PartyRepository partyRepository;

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

    /**
     * 팀 소속 신청
     */
    @Transactional
    public Long apply(Long boardId, Long userId, RecruitBoardApplyRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("신청 실패, 해당하는 유저가 없음"));
        RecruitBoard board = recruitBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("신청 실패, 해당하는 게시글이 없음"));

        Party party = partyRepository.save(Party.from(user, board, dto.getIsMeetRequired(), dto.getIsMeetOptional()));

        return party.getId();
    }

    /**
     * 특정 사용자에 대한 신청 승인
     */
    @Transactional
    public boolean approve(Long boardId, Long userId, Long targetUserId) {
        RecruitBoard board = recruitBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("신청 승인 실패, 해당하는 게시글이 없음"));

        if (board.getUser().getId().equals(userId) && !board.isCompleted()) {
            Party party = partyRepository.findByUserIdAndRecruitBoardId(targetUserId, boardId)
                    .orElseThrow(() -> new IllegalArgumentException("신청 승인 실패, 해당하는 신청 정보가 없음"));
            party.approve();
            board.increaseGathered();
            board.updateIsCompleted();

            return party.isApproved();
        } else {
            throw new IllegalArgumentException("신청 승인 실패, 게시글을 작성한 유저가 아니거나 팀 구성이 완료된 게시글입니다.");
        }
    }

}
