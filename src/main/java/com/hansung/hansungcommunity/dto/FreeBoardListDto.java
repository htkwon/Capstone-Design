package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.FreeBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class FreeBoardListDto {

    private Long id;
    private String title;
    private String content;

    //private User user ;
    private Long stuId;

    //TODO : 이미지
    private LocalDateTime createdDate;
    private int bookmarks;
    //TODO: 댓글
    private int report;

    public FreeBoardListDto(FreeBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        //this.user = board.getUser();
        this.stuId = board.getUser().getId();
        this.createdDate = board.getCreatedAt();
        this.bookmarks = board.getBookmarks();
        this.report = board.getReports();
    }
}
