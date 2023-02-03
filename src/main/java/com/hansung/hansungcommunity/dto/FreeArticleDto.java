package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.FreeArticle;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FreeArticleDto {
    private Long id;
    private String title;
    private String content;

    public FreeArticleDto(FreeArticle article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
