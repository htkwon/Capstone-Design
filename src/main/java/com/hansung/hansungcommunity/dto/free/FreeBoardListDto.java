package com.hansung.hansungcommunity.dto.free;

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
    private String writer;
    private String stuId;
    //TODO : 이미지
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int bookmark;
    private int reply;
    private int views;

    public FreeBoardListDto(FreeBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getUser().getNickname();
        this.stuId = board.getUser().getStudentId();
        this.createdDate = board.getCreatedAt();
        this.modifiedDate = board.getModifiedAt();
        this.bookmark = board.getBookmarks().size();
        this.reply = board.getReplies().size();
        this.views = board.getViews();
    }

}