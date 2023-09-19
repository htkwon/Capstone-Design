package com.hansung.hansungcommunity.dto.qna;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.*;

import java.time.LocalDateTime;

@Getter
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

    private QnaBoardDetailsDto(Long id, String title, String content, String writer, long stuId, String introduce, String profileImg, LocalDateTime createdDate, LocalDateTime modifiedDate, String language, int bookmark, int views, int reply) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.stuId = stuId;
        this.introduce = introduce;
        this.profileImg = profileImg;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.language = language;
        this.bookmark = bookmark;
        this.views = views;
        this.reply = reply;
    }
    public static QnaBoardDetailsDto from (QnaBoard board){
        return new QnaBoardDetailsDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUser().getNickname(),
                Long.parseLong(board.getUser().getStudentId()),
                board.getUser().getIntroduce(),
                board.getUser().getProfileImg(),
                board.getCreatedAt(),
                board.getModifiedAt(),
                board.getLanguage(),
                board.getBookmarks().size(),
                board.getViews(),
                board.getReplies().size()
        );
    }


}
