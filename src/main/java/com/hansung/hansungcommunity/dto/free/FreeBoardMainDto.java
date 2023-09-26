package com.hansung.hansungcommunity.dto.free;

import com.hansung.hansungcommunity.entity.FreeBoard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FreeBoardMainDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final String introduce;
    private final String stuId;
    private final String profileImg;
    private final LocalDateTime createdDate;
    private final int bookmark;
    private final int reply;

    private FreeBoardMainDto(FreeBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getUser().getNickname();
        this.introduce = board.getUser().getIntroduce();
        this.stuId = board.getUser().getStudentId();
        this.profileImg = board.getUser().getProfileImg();
        this.createdDate = board.getCreatedAt();
        this.bookmark = board.getBookmarks().size();
        this.reply = board.getReplies().size();
    }

    public static FreeBoardMainDto from(FreeBoard board) {
        return new FreeBoardMainDto(board);
    }

}
