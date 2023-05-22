package com.hansung.hansungcommunity.dto.free;

import com.hansung.hansungcommunity.entity.FreeBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class FreeBoardRequestDto {

    private Long id;

    @NotEmpty
    @Size(min = 3, max = 30)
    private String title;

    @NotEmpty
    @Min(1)
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
