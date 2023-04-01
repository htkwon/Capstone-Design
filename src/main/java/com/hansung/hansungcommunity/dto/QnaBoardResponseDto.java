package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QnaBoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdDate;
    private String language;
    private int bookmark;
    private int reply;
    private int point;


    public QnaBoardResponseDto(QnaBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getUser().getNickname();
        this.createdDate = board.getCreatedAt();
        this.language = board.getLanguage();
        this.bookmark = board.getBookmarks();
        this.reply = board.getReplies().size();
        this.point = board.getPoint();
    }

}
