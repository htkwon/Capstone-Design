package com.hansung.hansungcommunity.dto.free;

import com.hansung.hansungcommunity.dto.user.UserBookmarkDto;
import com.hansung.hansungcommunity.entity.FreeBoardBookmark;
import lombok.Data;

@Data
public class FreeBoardBookmarkDto {
    private UserBookmarkDto user;
    private FreeBoardRequestDto freeBoard;

    public FreeBoardBookmarkDto(UserBookmarkDto user, FreeBoardRequestDto freeBoard) {
        this.user = user;
        this.freeBoard = freeBoard;
    }


    public static FreeBoardBookmarkDto of(FreeBoardBookmark freeBoardBookmark) {
        return new FreeBoardBookmarkDto(
                UserBookmarkDto.of(freeBoardBookmark.getUser()),
                FreeBoardRequestDto.of(freeBoardBookmark.getFreeBoard())
        );
    }

}
