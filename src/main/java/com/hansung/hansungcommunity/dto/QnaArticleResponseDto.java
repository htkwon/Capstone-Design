package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.QnaArticle;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QnaArticleResponseDto {
    private Long id;
    private String nickname;
    private LocalDateTime time;
    private String title;
    private int bookmark;


    public QnaArticleResponseDto(QnaArticle article) {

        this.nickname = article.getUser().getNickname();
        this.time = article.getCreatedAt();
        this.title = article.getTitle();
        this.bookmark = article.getBookmarkHits();
    }


}
