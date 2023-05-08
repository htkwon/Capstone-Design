package com.hansung.hansungcommunity.dto.free;

import com.hansung.hansungcommunity.entity.FreeBoard;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FreeBoardDetailsDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String profileImg;
    private Long stuId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int reply;
    private int bookmark;
    private int views;

    public FreeBoardDetailsDto(FreeBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getUser().getNickname();
        this.stuId = Long.parseLong(board.getUser().getStudentId());
        this.profileImg = board.getUser().getProfileImg();
        this.createdDate = board.getCreatedAt();
        this.modifiedDate = board.getModifiedAt();
        this.bookmark = board.getBookmarks().size();
        this.reply = board.getReplies().size();
        this.views = board.getViews();
    }

}
