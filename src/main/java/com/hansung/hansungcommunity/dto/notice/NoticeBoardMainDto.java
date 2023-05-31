package com.hansung.hansungcommunity.dto.notice;

import com.hansung.hansungcommunity.entity.NoticeBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NoticeBoardMainDto {

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

    public NoticeBoardMainDto(NoticeBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getUser().getNickname();
        this.stuId = board.getUser().getStudentId();
        this.introduce = board.getUser().getIntroduce();
        this.profileImg = board.getUser().getProfileImg();
        this.createdDate = board.getCreatedAt();
        this.bookmark = board.getBookmarks().size();
        this.reply = board.getReplies().size();
    }

}
