package com.hansung.hansungcommunity.dto.qna;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Getter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class QnaBoardRequestDto {

    private Long id;
    @NotEmpty
    @Size(min = 3, max = 30)
    private String title;
    @NotEmpty
    private String content;
    private String language;

    private QnaBoardRequestDto(Long id, String title, String content, String language) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.language = language;
    }

    public static QnaBoardRequestDto from(QnaBoard board){
        return new QnaBoardRequestDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getLanguage()
        );
    }

}
