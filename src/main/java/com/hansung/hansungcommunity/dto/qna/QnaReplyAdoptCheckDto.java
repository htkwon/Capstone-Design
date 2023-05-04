package com.hansung.hansungcommunity.dto.qna;

import lombok.Data;

@Data
public class QnaReplyAdoptCheckDto {

    private Boolean check;
    private Long id;



    public QnaReplyAdoptCheckDto(Boolean check, Long id){
        this.check = check;
        this.id = id;
    }

}
