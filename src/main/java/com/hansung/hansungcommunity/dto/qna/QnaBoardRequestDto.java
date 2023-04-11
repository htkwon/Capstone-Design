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
    private int point;
    private String tag;
    private String language;

    //Testcode 및 생성의 편의를 위한 Factory method
    public static QnaBoardRequestDto of(Long id, String title, String content, int point, String tag, String language){
        return new QnaBoardRequestDto(id,title,content,point,tag,language);
    }

    public static QnaBoardRequestDto from(QnaBoard entity){
        return new QnaBoardRequestDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getPoint(),
                entity.getTag(),
                entity.getLanguage()
        );
    }

    public QnaBoard toEntity(){
        return QnaBoard.of(
                title,
                content,
                point,
                language
        );
    }

}
