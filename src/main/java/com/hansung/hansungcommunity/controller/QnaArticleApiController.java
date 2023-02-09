package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.dto.QnaArticleDto;
import com.hansung.hansungcommunity.dto.QnaArticleResponseDto;
import com.hansung.hansungcommunity.service.QnaArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class QnaArticleApiController {

    private final QnaArticleService qnaArticleService;

    //홈View 글 리스트 조회
    @GetMapping("qnaArticles")
    public ResponseEntity<List<QnaArticleResponseDto>> articles(){
        List<QnaArticleResponseDto> dtoList = qnaArticleService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }

    //게시글 단건 조회
    @GetMapping("/qnaArticles/{articleId}")
    public QnaArticleDto article(@PathVariable Long articleId){
        return qnaArticleService.getArticle(articleId);
    }

    //게시글 등록
    @PostMapping("/qnaArticles/{userId}")
    public ResponseEntity<QnaArticleDto> create(@RequestBody QnaArticleDto dto,@PathVariable Long userId){
        QnaArticleDto res = qnaArticleService.saveArticle(dto,userId);
        return ResponseEntity.status(HttpStatus.OK).body(res);

    }

    //TODO: 나머지 api들은 Front 팀으로 부터 view 받고 testCode 작성 하면서 동시에 production code 개발 예정

}
