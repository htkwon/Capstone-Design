package com.hansung.hansungcommunity.dto.qna;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QnaBoardDetailsDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private Long stuId;
    private String introduce;
    private String profileImg;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String language;
    private int bookmark;
    private int views;
    private int reply;

    public QnaBoardDetailsDto(QnaBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getUser().getNickname();
        this.stuId = Long.parseLong(board.getUser().getStudentId());
        this.introduce = board.getUser().getIntroduce();
        this.profileImg = board.getUser().getProfileImg();
        this.createdDate = board.getCreatedAt();
        this.modifiedDate = board.getModifiedAt();
        this.language = board.getLanguage();
        this.bookmark = board.getBookmarks().size();
        this.views = board.getViews();
        this.reply = board.getReplies().size();
    }

}
