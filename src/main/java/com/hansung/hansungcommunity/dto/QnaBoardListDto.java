package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class QnaBoardListDto {

    private Long id;
    private String title;
    private String content;

    private Long stuId;

    //TODO : 파일
    private LocalDateTime createdDate;
    private int bookmarks;
    //TODO: 댓글
    private int report;

    public QnaBoardListDto(QnaBoard board){
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
