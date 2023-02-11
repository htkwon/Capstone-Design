package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.dto.FreeBoardDto;
import com.hansung.hansungcommunity.dto.FreeBoardResponseDto;
import com.hansung.hansungcommunity.service.FreeBoardService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor // 생성자 주입 (final 키워드)
@RequestMapping("/api")
public class FreeBoardApiController {

    private final FreeBoardService freeBoardService;

    /**
     * 모든 게시글 조회
     */
    @GetMapping("/freeBoards")
    public ResponseEntity<Result<List<FreeBoardResponseDto>>> list() {
        List<FreeBoardResponseDto> dtoList = freeBoardService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(dtoList));
    }

    /**
     * 게시글 저장
     */
    @PostMapping("/freeBoards")
    public ResponseEntity<Result<FreeBoardDto>> create(
            @RequestParam(name = "uid") Long userId, // 유저 id도 필요한가 ?
            @RequestBody FreeBoardDto dto
    ) {
        FreeBoardDto boardDto  = freeBoardService.post(userId, dto);

        // Wrapper 클래스로 감싼 후,
        // ResponseEntity 의 body 에 담아 반환
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(boardDto));
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/freeBoards/{boardId}")
    public ResponseEntity<Result<FreeBoardDto>> update(
            @PathVariable("boardId") Long boardId,
            @RequestBody FreeBoardDto dto
    ) {
        FreeBoardDto boardDto  = freeBoardService.update(boardId, dto);

        // Wrapper 클래스로 감싼 후,
        // ResponseEntity 의 body 에 담아 반환
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(boardDto));
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/freeBoards/{boardId}")
    public ResponseEntity<Result<FreeBoardDto>> delete(@PathVariable("boardId") Long boardId) {
        FreeBoardDto boardDto = freeBoardService.delete(boardId);

        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(boardDto));
    }

    // 확장성을 위한 Wrapper 클래스
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

}
