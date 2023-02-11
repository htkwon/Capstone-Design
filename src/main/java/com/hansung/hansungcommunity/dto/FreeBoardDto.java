package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.FreeBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FreeBoardDto {
    private Long id;
    private String title;
    private String content;

    public FreeBoardDto(FreeBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
