package com.hansung.hansungcommunity.dto.qna;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Data;

/**
 * 조회수가 높은 게시글 조회를 위한 DTO
 */
@Data
public class QnaBoardMostViewedDto {

    private Long id;
    private String title;
    private String writer;
    private String language;

    private QnaBoardMostViewedDto(Long id, String nickname, String title, String language) {
        this.id = id;
        this.writer = nickname;
        this.title = title;
        this.language = language;
    }

    public static QnaBoardMostViewedDto of(QnaBoard board) {
        return new QnaBoardMostViewedDto(
                board.getId(),
                board.getUser().getNickname(),
                board.getTitle(),
                board.getLanguage()
        );
    }

}
