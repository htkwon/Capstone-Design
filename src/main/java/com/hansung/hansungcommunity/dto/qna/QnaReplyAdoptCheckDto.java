package com.hansung.hansungcommunity.dto.qna;

import lombok.Getter;

@Getter
public class QnaReplyAdoptCheckDto {

    private Boolean check;
    private Long id;

    private QnaReplyAdoptCheckDto(Boolean check, Long id){
        this.check = check;
        this.id = id;
    }

    public static QnaReplyAdoptCheckDto of(Boolean check, Long id){
        return new QnaReplyAdoptCheckDto(check,id);
    }

}
