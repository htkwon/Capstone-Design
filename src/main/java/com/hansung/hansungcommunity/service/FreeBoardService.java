package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.free.*;
import com.hansung.hansungcommunity.entity.FreeBoard;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.FreeBoardRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // 생성자 주입 (final 키워드)
@Transactional(readOnly = true) // 읽기 전용
public class FreeBoardService {

    private final FreeBoardRepository freeBoardRepository;
    private final UserRepository userRepository;

    /**
     * 자유 게시글 게시
     */
    @Transactional // 필요 시 쓰기 전용
    public Long post(Long userId, FreeBoardRequestDto boardDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 학생이 없습니다."));
        FreeBoard board = FreeBoard.createBoard(user, boardDto); // 게시글 생성

        FreeBoard savedBoard = freeBoardRepository.save(board); // DB에 저장

        return savedBoard.getId();
    }

    /**
     * 자유 게시글 수정
     */
    @Transactional
    public FreeBoardRequestDto update(Long boardId, FreeBoardRequestDto dto) {
        FreeBoard target = freeBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 수정 실패, 해당하는 게시글이 없음"));

        // 게시글 수정
        target.patch(dto);

        return dto;
    }

    /**
     * 자유 게시글 삭제
     */
    @Transactional
    public FreeBoardRequestDto delete(Long boardId) {
        FreeBoard target = freeBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 삭제 실패, 해당하는 게시글이 없음"));

        // 게시글 삭제
        freeBoardRepository.delete(target);

        return new FreeBoardRequestDto(target);
    }

    /**
     * 게시글 리스트 조회
     * 정렬 후, 4개의 게시글만 반환
     */
    public List<FreeBoardMainDto> findAll() {
        Pageable pageable = PageRequest.of(0, 4, Sort.Direction.DESC, "createdAt");
        return freeBoardRepository.findAll(pageable).getContent()
                .stream()
                .map(FreeBoardMainDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 특정 게시글 조회
     */
    public FreeBoardDetailsDto findOne(Long boardId) {
        FreeBoard board = freeBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 조회 실패, 해당하는 게시글이 없음"));

        return new FreeBoardDetailsDto(board);
    }

    /**
     * 조회수 증가 로직
     * Auditing 수정 시간 업데이트, 논의 후 해결 요망
     */
    @Transactional
    public void increaseHits(Long boardId) {
        FreeBoard board = freeBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("조회수 증가 실패, 해당하는 게시글이 없음"));

        board.increaseHits();
    }

    /**
     * 게시글 리스트 조회
     * 프론트에서 요청한 페이지 정보에 맞게 게시글 반환
     */
    public List<FreeBoardListDto> findByPage(Pageable pageable) {
        Pageable setPage = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "createdAt");
        return freeBoardRepository.findAll(setPage).getContent()
                .stream()
                .map(FreeBoardListDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 게시글 수정 시, 기존 게시글 정보 반환
     */
    public FreeBoardUpdateDto findOneForUpdate(Long boardId) {
        FreeBoard board = freeBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 조회 실패, 해당하는 게시글이 없음"));

        return new FreeBoardUpdateDto(board);
    }

    /**
     * 전체 게시글 수
     */
    public long getTotal() {
        return freeBoardRepository.count();
    }

}

