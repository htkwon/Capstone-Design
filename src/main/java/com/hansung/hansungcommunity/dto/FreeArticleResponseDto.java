package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.FreeArticle;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FreeArticleResponseDto {
    private Long id;
    private String nickname;
    private LocalDateTime time;
    private String title;
    private int bookmark;
//  private int comment; // 댓글 기능 구현 후 추가

    public FreeArticleResponseDto(FreeArticle article) {
        this.nickname = article.getUser().getNickname();
        this.time = article.getCreatedAt();
        this.title = article.getTitle();
        this.bookmark = article.getBookmarkHits();
    }

}
