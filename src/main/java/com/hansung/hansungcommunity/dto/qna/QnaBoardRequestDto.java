package com.hansung.hansungcommunity.dto.qna;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class QnaBoardRequestDto {

    private Long id;
    private String title;
    private String content;
    private String tag;
    private String language;

    public QnaBoardRequestDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    //Testcode 및 생성의 편의를 위한 Factory method
    public static QnaBoardRequestDto of(Long id, String title, String content, String tag, String language) {
        return new QnaBoardRequestDto(id, title, content, tag, language);
    }

    //QnaBoardBookmarkDto 에서 사용
    public static QnaBoardRequestDto of(QnaBoard qnaBoard) {
        return new QnaBoardRequestDto(
                qnaBoard.getId(),
                qnaBoard.getTitle(),
                qnaBoard.getContent()
        );
    }

    public static QnaBoardRequestDto from(QnaBoard entity) {
        return new QnaBoardRequestDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getTag(),
                entity.getLanguage()
        );
    }

    public QnaBoard toEntity() {
        return QnaBoard.of(
                title,
                content,
                language
        );
    }

}
