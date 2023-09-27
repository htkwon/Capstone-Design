package com.hansung.hansungcommunity.dto.qna;

import lombok.Getter;

@Getter
public class QnaReplyAdoptCheckDto {

    private final Boolean check;
    private final Long id;

    private QnaReplyAdoptCheckDto(Boolean check, Long id){
        this.check = check;
        this.id = id;
    }

    public static QnaReplyAdoptCheckDto of(boolean check, Long id){
        return new QnaReplyAdoptCheckDto(check,id);
    }

}
