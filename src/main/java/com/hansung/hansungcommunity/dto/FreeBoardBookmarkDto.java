package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.FreeBoardBookmark;
import lombok.Data;

@Data
public class FreeBoardBookmarkDto {
    private UserDto user;
    private FreeBoardDto freeBoard;

    public FreeBoardBookmarkDto(UserDto user, FreeBoardDto freeBoard) {
        this.user = user;
        this.freeBoard = freeBoard;
    }


    public static FreeBoardBookmarkDto of(FreeBoardBookmark freeBoardBookmark){
        return new FreeBoardBookmarkDto(
                UserDto.of(freeBoardBookmark.getUser()),
                FreeBoardDto.of(freeBoardBookmark.getFreeBoard())
        );
    }

}
