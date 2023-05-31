package com.hansung.hansungcommunity.dto.recruit;


import com.hansung.hansungcommunity.entity.RecruitBoard;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecruitBoardMainDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String stuId;
    private String introduce;
    private String profileImg;
    private LocalDateTime createdDate;
    private int bookmark;
    private int reply;

    public RecruitBoardMainDto(RecruitBoard board) {
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

}
