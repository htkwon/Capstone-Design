package com.hansung.hansungcommunity.dto.free;

import com.hansung.hansungcommunity.entity.FreeBoard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FreeBoardDetailsDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final String introduce;
    private final String profileImg;
    private final Long stuId;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final int reply;
    private final int bookmark;
    private final int views;

    private FreeBoardDetailsDto(FreeBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getUser().getNickname();
        this.stuId = Long.parseLong(board.getUser().getStudentId());
        this.introduce = board.getUser().getIntroduce();
        this.profileImg = board.getUser().getProfileImg();
        this.createdDate = board.getCreatedAt();
        this.modifiedDate = board.getModifiedAt();
        this.bookmark = board.getBookmarks().size();
        this.reply = board.getReplies().size();
        this.views = board.getViews();
    }

    public static FreeBoardDetailsDto from(FreeBoard board) {
        return new FreeBoardDetailsDto(board);
    }

}
