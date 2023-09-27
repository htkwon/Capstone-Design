package com.hansung.hansungcommunity.dto.qna;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Getter;

/**
 * 게시글 수정 시, 기존 게시글 정보를 담아주는 DTO
 */

@Getter
public class QnaBoardUpdateDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String language;

    private QnaBoardUpdateDto(Long id, String title, String content, String language) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.language = language;
    }

    public static QnaBoardUpdateDto from(QnaBoard board){
        return new QnaBoardUpdateDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getLanguage()
        );
    }

}
