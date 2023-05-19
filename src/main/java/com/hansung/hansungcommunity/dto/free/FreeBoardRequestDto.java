package com.hansung.hansungcommunity.dto.free;

import com.hansung.hansungcommunity.entity.FreeBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FreeBoardRequestDto {

    private Long id;
    private String title;
    private String content;

    public FreeBoardRequestDto(FreeBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }

    public FreeBoardRequestDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static FreeBoardRequestDto of(FreeBoard freeBoard) {
        return new FreeBoardRequestDto(
                freeBoard.getId(),
                freeBoard.getTitle(),
                freeBoard.getContent()
        );
    }

}
