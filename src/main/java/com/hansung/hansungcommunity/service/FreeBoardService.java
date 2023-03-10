package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.FreeBoardDto;
import com.hansung.hansungcommunity.dto.FreeBoardListDto;
import com.hansung.hansungcommunity.dto.FreeBoardResponseDto;
import com.hansung.hansungcommunity.entity.FreeBoard;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.FreeBoardRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public FreeBoardDto post(Long userId, FreeBoardDto boardDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 게시 실패, 해당하는 유저가 없음"));

        FreeBoard board = FreeBoard.createBoard(user, boardDto); // 게시글 생성

        FreeBoard savedBoard = freeBoardRepository.save(board); // DB에 저장

        return new FreeBoardDto(savedBoard); // DTO 변환 후 반환
    }

    /**
     * 자유 게시글 수정
     */
    @Transactional
    public FreeBoardDto update(Long boardId, FreeBoardDto dto) {
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
    public FreeBoardDto delete(Long boardId) {
        FreeBoard target = freeBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 삭제 실패, 해당하는 게시글이 없음"));

        // 게시글 삭제
        freeBoardRepository.delete(target);

        return new FreeBoardDto(target);
    }

    /**
     * 게시글 리스트 조회
     * 정렬 후, 4개의 게시글만 반환
     */
    public List<FreeBoardResponseDto> findAll() {
        Pageable pageable = PageRequest.of(0,4, Sort.Direction.DESC,"createdAt");
        return freeBoardRepository.findAll(pageable).getContent()
                .stream()
                .map(FreeBoardResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 특정 게시글 조회
     */
    public FreeBoardResponseDto findOne(Long boardId) {
        FreeBoard board = freeBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 조회 실패, 해당하는 게시글이 없음"));

        return new FreeBoardResponseDto(board);
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
    public List<FreeBoardListDto> findByPage(Pageable pageable){
        Pageable setPage = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),Sort.Direction.ASC,"createdAt");
        return freeBoardRepository.findAll(setPage).getContent()
                .stream()
                .map(FreeBoardListDto::new)
                .collect(Collectors.toList());
    }


}

