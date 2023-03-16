package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QnaBoardDetailsDto {
    private Long id;
    private String title;
    private String content;
    //    private String writer;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String language;
    private int point;
    private int bookmark;

    public QnaBoardDetailsDto(QnaBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdDate = board.getCreatedAt();
        this.modifiedDate = board.getModifiedAt();
        this.language = board.getLanguage();
        this.bookmark = board.getBookmarks();
        this.point = board.getPoint();
    }

}
