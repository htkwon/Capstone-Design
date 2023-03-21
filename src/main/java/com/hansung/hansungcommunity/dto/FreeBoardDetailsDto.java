package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.FreeBoard;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FreeBoardDetailsDto {
    private Long id;
    private String title;
    private String content;
//    private String writer;
//    private String profileImg;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int report;
    private int bookmark;

    public FreeBoardDetailsDto(FreeBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdDate = board.getCreatedAt();
        this.modifiedDate = board.getModifiedAt();
        this.report = board.getReports();
        this.bookmark = board.getBookmarks();
    }
}
