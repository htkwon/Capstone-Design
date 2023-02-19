package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QnaBoardResponseDto {
    private Long id;
    private String nickname;
    private LocalDateTime createdDate;
    private String title;
    private int bookmarks;


    public QnaBoardResponseDto(QnaBoard board) {

        this.nickname = board.getUser().getNickname();
        this.createdDate = board.getCreatedAt();
        this.title = board.getTitle();
        this.bookmarks = board.getBookmarks();
    }


}
