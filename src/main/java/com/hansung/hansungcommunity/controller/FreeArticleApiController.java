package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.dto.FreeArticleDto;
import com.hansung.hansungcommunity.service.FreeArticleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor // 생성자 주입 (final 키워드)
public class FreeArticleApiController {

    private final FreeArticleService freeArticleService;

    /**
     * 게시글 저장
     */
    @PostMapping("/api/articles")
    public ResponseEntity<Result<FreeArticleDto>> create(
            @RequestParam(name = "uid") Long userId, // 유저 id도 필요한가 ?
            @RequestBody FreeArticleDto dto
    ) {
        FreeArticleDto articleDto  = freeArticleService.post(userId, dto);

        // Wrapper 클래스로 감싼 후,
        // ResponseEntity 의 body 에 담아 반환
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(articleDto));
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/api/articles/{article-id}")
    public ResponseEntity<Result<FreeArticleDto>> update(
            @PathVariable("article-id") Long articleId,
            @RequestBody FreeArticleDto dto
    ) {
        FreeArticleDto articleDto  = freeArticleService.update(articleId, dto);

        // Wrapper 클래스로 감싼 후,
        // ResponseEntity 의 body 에 담아 반환
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(articleDto));
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/api/articles/{article-id}")
    public ResponseEntity<Result<FreeArticleDto>> delete(@PathVariable("article-id") Long articleId) {
        FreeArticleDto articleDto = freeArticleService.delete(articleId);

        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(articleDto));
    }

    // 확장성을 위한 Wrapper 클래스
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

}
