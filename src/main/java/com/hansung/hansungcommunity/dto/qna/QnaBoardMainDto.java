package com.hansung.hansungcommunity.dto.qna;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QnaBoardMainDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String stuId;
    private String profileImg;
    private LocalDateTime createdDate;
    private String language;
    private int bookmark;
    private int reply;

    public QnaBoardMainDto(QnaBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getUser().getNickname();
        this.stuId = board.getUser().getStudentId();
        this.createdDate = board.getCreatedAt();
        this.profileImg = board.getUser().getProfileImg();
        this.language = board.getLanguage();
        this.bookmark = board.getBookmarks().size();
        this.reply = board.getReplies().size();
    }

}
