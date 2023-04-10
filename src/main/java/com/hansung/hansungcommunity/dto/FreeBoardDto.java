package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.FreeBoard;
import com.hansung.hansungcommunity.entity.FreeBoardBookmark;
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

    public FreeBoardDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content =  content;
    }

    public static FreeBoardDto of(FreeBoard freeBoard) {
        return new FreeBoardDto(
                freeBoard.getId(),
                freeBoard.getTitle(),
                freeBoard.getContent()
        );
    }
}
