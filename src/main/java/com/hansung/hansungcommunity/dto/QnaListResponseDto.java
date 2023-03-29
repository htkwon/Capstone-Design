package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * QnaBoard 게시글 리스트 조회를 위한 DTO
 */
@Data
public class QnaListResponseDto {

    private Long id;
    private String nickname;
    private LocalDateTime time;
    private String title;
    private String content;
    private String language;
    private int bookmark;
    private int comment;

    private QnaListResponseDto(Long id, String nickname, LocalDateTime time, String title, String content, String language, int bookmark, int comment) {
        this.id = id;
        this.nickname = nickname;
        this.time = time;
        this.title = title;
        this.content = content;
        this.language = language;
        this.bookmark = bookmark;
        this.comment = comment;
    }

    public static QnaListResponseDto of(QnaBoard board) {
        return new QnaListResponseDto(
                board.getId(),
                board.getUser().getNickname(),
                board.getCreatedAt(),
                board.getTitle(),
                board.getContent(),
                "C",
                board.getBookmarks(),
                board.getReplies().size()
        );
    }

}
