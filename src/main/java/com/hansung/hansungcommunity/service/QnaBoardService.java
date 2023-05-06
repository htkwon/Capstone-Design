package com.hansung.hansungcommunity.service;


import com.hansung.hansungcommunity.dto.qna.*;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.QnaBoardRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QnaBoardService {

    private final UserRepository userRepository;
    private final QnaBoardRepository qnaBoardRepository;

    /**
     * 특정 게시글 조회
     */
    public QnaBoardDetailsDto findOne(Long boardId) {
        QnaBoard board = qnaBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 조회 실패, 해당하는 게시글이 없음"));

        return new QnaBoardDetailsDto(board);
    }

    /**
     * 정렬 된 4개 Qna 게시글 반환
     */
    public List<QnaBoardMainDto> findAll() {
        Pageable pageable = PageRequest.of(0, 4, Sort.Direction.DESC, "createdAt");
        return qnaBoardRepository.findAll(pageable).getContent()
                .stream()
                .map(QnaBoardMainDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 조회수를 기준으로 4개의 게시글 조회
     */
    public List<QnaBoardMostViewedDto> findMostViewedBoards() {
        Pageable pageable = PageRequest.of(0, 4, Sort.Direction.DESC, "views");
        LocalDateTime standardTime = LocalDateTime.now().minusWeeks(1); // 1주일 기준

        return qnaBoardRepository.findByCreatedAtAfter(standardTime, pageable)
                .stream()
                .map(QnaBoardMostViewedDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 게시글 저장
     */
    @Transactional
    public Long post(Long userId, QnaBoardRequestDto dto) {
        User user = userRepository.getReferenceById(userId);

        QnaBoard board = dto.toEntity();
        board.setUser(user);

        QnaBoard savedBoard = qnaBoardRepository.save(board);
        return savedBoard.getId();
    }

    /**
     * user 컬럼만 비어있는 qnaBoard 엔티티에 유저 매핑
     */
    @Transactional
    public void mappingUser(Long userId, QnaBoard entity) {
        User user = userRepository.getReferenceById(userId);

        entity.setUser(user);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Long update(Long boardId, QnaBoardRequestDto dto) {
        QnaBoard target = qnaBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 수정 실패, 해당하는 게시글이 없음"));

        target.updateBoard(dto);

        return boardId;
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void delete(Long boardId) {
        qnaBoardRepository.deleteById(boardId);
    }

    /**
     * 조회수 증가 로직
     * Auditing 수정 시간 업데이트, 논의 후 해결 요망
     */
    @Transactional
    public void increaseHits(Long boardId) {
        QnaBoard board = qnaBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("조회수 증가 실패, 해당하는 게시글이 없음"));

        board.increaseHits();
    }

    /**
     * 게시글 리스트 조회
     * 프론트에서 요청한 페이지 정보에 맞게 게시글 반환
     */
    public List<QnaBoardListDto> findByPage(Pageable pageable) {
        Pageable setPage = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "createdAt");
        return qnaBoardRepository.findAll(setPage).getContent()
                .stream()
                .map(QnaBoardListDto::new)
                .collect(Collectors.toList());
    }

    public Long getUserId(Long boardId) {
        QnaBoard qnaBoard = qnaBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        return qnaBoard.getUser().getId();
    }

    /**
     * 게시글 수정 시, 기존 게시글 정보 반환
     */
    public QnaBoardUpdateDto findOneForUpdate(Long boardId) {
        QnaBoard board = qnaBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 조회 실패, 해당하는 게시글이 없음"));

        return new QnaBoardUpdateDto(board);
    }

    /**
     * 전체 게시글 수
     */
    public long getTotal() {
        return qnaBoardRepository.count();
    }

}
