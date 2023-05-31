package com.hansung.hansungcommunity.dto.notice;

import com.hansung.hansungcommunity.entity.NoticeBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NoticeBoardDetailsDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String introduce;
    private String profileImg;
    private Long stuId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int reply;
    private int bookmark;
    private int views;

    public NoticeBoardDetailsDto(NoticeBoard board) {
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

}
