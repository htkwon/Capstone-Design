package com.hansung.hansungcommunity.dto.user;

import com.hansung.hansungcommunity.entity.Board;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserActivityDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String writer;
    private int bookmark;
    private int reply;
    private String boardType;

    private UserActivityDto(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, String writer, int bookmark, int reply, String boardType) {
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

    public static UserActivityDto of(Board board) {
        return new UserActivityDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getCreatedAt(),
                board.getModifiedAt(),
                board.getUser().getNickname(),
                board.getBookmarks().size(),
                board.getReplies().size(),
                typeConvert(board.getBoardType())
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
        switch (type) {
            case "FreeBoard":
                return "free";
            case "QnaBoard":
                return "questions";
            case "RecruitBoard":
                return "recruit";
            default:
                return "notice";
        }
    }

}
