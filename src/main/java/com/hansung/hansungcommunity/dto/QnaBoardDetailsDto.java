package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QnaBoardDetailsDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String language;
    private int bookmark;
    private int views;
    private int reply;
    private int point;

    public QnaBoardDetailsDto(QnaBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getUser().getNickname();
        this.createdDate = board.getCreatedAt();
        this.modifiedDate = board.getModifiedAt();
        this.language = board.getLanguage();
        this.bookmark = board.getBookmarks();
        this.views = board.getHits();
        this.reply = board.getReplies().size();
        this.point = board.getPoint();
    }

}
