package com.hansung.hansungcommunity.dto.qna;

import com.hansung.hansungcommunity.dto.user.UserBookmarkDto;
import com.hansung.hansungcommunity.entity.QnaBoardBookmark;
import lombok.Data;

@Data
public class QnaBoardBookmarkDto {

    private UserBookmarkDto user;
    private QnaBoardRequestDto qnaBoard;

    public QnaBoardBookmarkDto(UserBookmarkDto user, QnaBoardRequestDto qnaBoard) {
        this.user = user;
        this.qnaBoard = qnaBoard;
    }

    public static QnaBoardBookmarkDto of(QnaBoardBookmark qnaBoardBookmark) {
        return new QnaBoardBookmarkDto(
                UserBookmarkDto.of(qnaBoardBookmark.getUser()),
                QnaBoardRequestDto.of(qnaBoardBookmark.getQnaBoard())
        );
    }

}
