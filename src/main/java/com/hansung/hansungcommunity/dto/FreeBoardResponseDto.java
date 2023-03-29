package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.FreeBoard;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FreeBoardResponseDto {

    private Long id;
    private String nickname;
    private LocalDateTime createdDate;
    private String title;
    private int bookmark;
//  private int comment; // 댓글 기능 구현 후 추가

    public FreeBoardResponseDto(FreeBoard board) {
        this.id = board.getId();
        this.nickname = board.getUser().getNickname();
        this.createdDate = board.getCreatedAt();
        this.title = board.getTitle();
        this.bookmark = board.getBookmarks();
    }

}
