package com.hansung.hansungcommunity.dto.qna;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Data;

/**
 * 게시글 수정 시, 기존 게시글 정보를 담아주는 DTO
 */

@Data
public class QnaBoardUpdateDto {

    private Long id;
    private String title;
    private String content;
    private String language;

    public QnaBoardUpdateDto(QnaBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.language = board.getLanguage();
    }

}
