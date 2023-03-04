package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class QnaBoardDto {


    private Long id;
    private String title;
    private String content;
    private int point;
    private String tag;



    //Testcode 및 생성의 편의를 위한 Factory method
    public static QnaBoardDto of(Long id, String title, String content, int point, String tag){
        return new QnaBoardDto(id,title,content,point,tag);
    }

    public static QnaBoardDto from(QnaBoard entity){
        return new QnaBoardDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getPoint(),
                entity.getTag()
        );
    }

    public QnaBoard toEntity(){
        return QnaBoard.of(
                title,
                content,
                point
        );
    }







}
