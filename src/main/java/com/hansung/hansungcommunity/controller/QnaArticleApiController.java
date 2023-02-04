package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.dto.QnaArticleDto;
import com.hansung.hansungcommunity.entity.QnaArticle;
import com.hansung.hansungcommunity.service.QnaArticleService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@Controller
public class QnaArticleApiController {

    private final QnaArticleService qnaArticleService;


    @GetMapping("/qnaArticles/{articleId}")
    public QnaArticleDto article(@PathVariable Long articleId){
        return qnaArticleService.getArticle(articleId);
    }

    /*
    @PostMapping("/qnaArticles")
    public void article(@RequestParam QnaArticleDto dto){
        qnaArticleService.saveArticle(dto);
    }
    */

    //TODO: 나머지 api들은 Front 팀으로 부터 view 받고 testCode 작성 하면서 동시에 production code 개발 예정
    //TODO : page 타입으로 게시글 리스트 조회







}
