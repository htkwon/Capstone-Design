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
    private String introduce;
    private String profileImg;
    private LocalDateTime createdDate;
    private String language;
    private int bookmark;
    private int reply;

    private QnaBoardMainDto(Long id, String title, String content, String writer, String stuId, String introduce, String profileImg, LocalDateTime createdDate, String language, int bookmark, int reply) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.stuId = stuId;
        this.introduce = introduce;
        this.profileImg = profileImg;
        this.createdDate = createdDate;
        this.language = language;
        this.bookmark = bookmark;
        this.reply = reply;
    }

    public static QnaBoardMainDto from(QnaBoard board){
        return new QnaBoardMainDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUser().getNickname(),
                board.getUser().getStudentId(),
                board.getUser().getIntroduce(),
                board.getUser().getProfileImg(),
                board.getCreatedAt(),
                board.getLanguage(),
                board.getBookmarks().size(),
                board.getReplies().size()
        );
    }


}
