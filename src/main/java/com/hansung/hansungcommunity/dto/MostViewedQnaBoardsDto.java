package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Data;

/**
 * 조회수가 높은 게시글 조회를 위한 DTO
 */
@Data
public class MostViewedQnaBoardsDto {

    private Long id;
    private String nickname;
    private String title;
    private String language;

    private MostViewedQnaBoardsDto(Long id, String nickname, String title, String language) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.language = language;
    }

    public static MostViewedQnaBoardsDto of(QnaBoard board) {
        return new MostViewedQnaBoardsDto(
                board.getId(),
                board.getUser().getNickname(),
                board.getTitle(),
                "C" // language 필드 생성 후 수정 필요
        );
    }

}
