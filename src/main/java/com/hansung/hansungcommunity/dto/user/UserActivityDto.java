package com.hansung.hansungcommunity.dto.user;

import com.hansung.hansungcommunity.entity.FreeBoard;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import lombok.Data;

import java.time.LocalDateTime;

@Data

public class UserActivityDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String writer;
    private String language;
    private int bookmark;
    private int reply;
    private String boardType;

    public UserActivityDto(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, String writer, int bookmark, int reply, String boardType) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdAt;
        this.modifiedDate = modifiedAt;
        this.writer = writer;
        this.bookmark = bookmark;
        this.reply = reply;
        this.boardType = boardType;
    }

    public UserActivityDto(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, String writer, String language, int bookmark, int reply, String boardType) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdAt;
        this.modifiedDate = modifiedAt;
        this.writer = writer;
        this.language = language;
        this.bookmark = bookmark;
        this.reply = reply;
        this.boardType = boardType;
    }

    public static UserActivityDto of(FreeBoard freeBoard) {
        return new UserActivityDto(
                freeBoard.getId(),
                freeBoard.getTitle(),
                freeBoard.getContent(),
                freeBoard.getCreatedAt(),
                freeBoard.getModifiedAt(),
                freeBoard.getUser().getNickname(),
                freeBoard.getBookmarks().size(),
                freeBoard.getReplies().size(),
                typeConvert(freeBoard.getBoardType())
        );
    }

    public static UserActivityDto of(QnaBoard qnaBoard) {
        return new UserActivityDto(
                qnaBoard.getId(),
                qnaBoard.getTitle(),
                qnaBoard.getContent(),
                qnaBoard.getCreatedAt(),
                qnaBoard.getModifiedAt(),
                qnaBoard.getUser().getNickname(),
                qnaBoard.getLanguage(),
                qnaBoard.getBookmarks().size(),
                qnaBoard.getReplies().size(),
                typeConvert(qnaBoard.getBoardType())
        );
    }

    public static UserActivityDto of(RecruitBoard recruitBoard) {
        return new UserActivityDto(
                recruitBoard.getId(),
                recruitBoard.getTitle(),
                recruitBoard.getContent(),
                recruitBoard.getCreatedAt(),
                recruitBoard.getModifiedAt(),
                recruitBoard.getUser().getNickname(),
                recruitBoard.getBookmarks().size(),
                recruitBoard.getReplies().size(),
                typeConvert(recruitBoard.getBoardType())
        );
    }

    private static String typeConvert(String type) {
        if (type.equals("FreeBoard")) {
            return "free";
        } else if (type.equals("QnaBoard")) {
            return "questions";
        } else {
            return "recruit";
        }
    }

}
