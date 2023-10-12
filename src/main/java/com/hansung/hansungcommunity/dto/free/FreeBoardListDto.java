package com.hansung.hansungcommunity.dto.free;

import com.hansung.hansungcommunity.dto.media.ImageDto;
import com.hansung.hansungcommunity.entity.FreeBoard;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class FreeBoardListDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final String introduce;
    private final String stuId;
    private final String profileImg;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final int bookmark;
    private final int reply;
    private final int views;

    private List<ImageDto> image;

    private FreeBoardListDto(FreeBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getUser().getNickname();
        this.stuId = board.getUser().getStudentId();
        this.introduce = board.getUser().getIntroduce();
        this.profileImg = board.getUser().getProfileImg();
        this.createdDate = board.getCreatedAt();
        this.modifiedDate = board.getModifiedAt();
        this.bookmark = board.getBookmarks().size();
        this.reply = board.getReplies().size();
        this.views = board.getViews();
        this.image = null;
    }

    public static FreeBoardListDto from(FreeBoard board) {
        return new FreeBoardListDto(board);
    }

    public void setImage(List<ImageDto> image) {
        this.image = image;
    }

}
