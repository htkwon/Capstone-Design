package com.hansung.hansungcommunity.dto.recruit;


import com.hansung.hansungcommunity.entity.RecruitBoard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecruitBoardMainDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final String stuId;
    private final String introduce;
    private final String profileImg;
    private final LocalDateTime createdDate;
    private final int bookmark;
    private final int reply;

    private RecruitBoardMainDto(RecruitBoard board) {
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

    public static RecruitBoardMainDto from(RecruitBoard board) {
        return new RecruitBoardMainDto(board);
    }

}
