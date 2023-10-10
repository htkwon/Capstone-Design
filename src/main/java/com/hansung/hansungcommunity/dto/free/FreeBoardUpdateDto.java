package com.hansung.hansungcommunity.dto.free;

import com.hansung.hansungcommunity.entity.FreeBoard;
import lombok.Getter;

/**
 * 게시글 수정 시, 기존 게시글 정보를 담아주는 DTO
 */
@Getter
public class FreeBoardUpdateDto {

    private final Long id;
    private final String title;
    private final String content;

    private FreeBoardUpdateDto(FreeBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }

    public static FreeBoardUpdateDto from(FreeBoard board) {
        return new FreeBoardUpdateDto(board);
    }

}
