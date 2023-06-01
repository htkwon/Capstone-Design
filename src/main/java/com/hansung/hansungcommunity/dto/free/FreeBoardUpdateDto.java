package com.hansung.hansungcommunity.dto.free;

import com.hansung.hansungcommunity.entity.FreeBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 게시글 수정 시, 기존 게시글 정보를 담아주는 DTO
 */
@Data
@NoArgsConstructor
public class FreeBoardUpdateDto {

    private Long id;
    private String title;
    private String content;

    public FreeBoardUpdateDto(FreeBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }

}
