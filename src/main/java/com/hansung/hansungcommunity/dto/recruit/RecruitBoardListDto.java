package com.hansung.hansungcommunity.dto.recruit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hansung.hansungcommunity.dto.ImageDto;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RecruitBoardListDto {

    private final Long id;
    private final String title;
    private final String writer;
    private final String introduce;
    private final String profileImg;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final int bookmark;
    private final int reply;
    private final int views;
    private final Long stuId;
    private final String require;
    private final String optional;
    private final int party;
    private int gathered;
    @JsonProperty("isCompleted")
    private boolean isCompleted;
    private List<ImageDto> image;

    private RecruitBoardListDto(Long id, String title, String writer, String introduce, String profileImg, LocalDateTime createdDate, LocalDateTime modifiedDate, int bookmark, int reply, int views, Long stuId, String require, String optional, int party, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.introduce = introduce;
        this.profileImg = profileImg;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.bookmark = bookmark;
        this.reply = reply;
        this.views = views;
        this.stuId = stuId;
        this.require = require;
        this.optional = optional;
        this.party = party;
        this.isCompleted = isCompleted;
        this.image = null;
    }

    public static RecruitBoardListDto from(RecruitBoard recruitBoard) {
        return new RecruitBoardListDto(
                recruitBoard.getId(),
                recruitBoard.getTitle(),
                recruitBoard.getUser().getNickname(),
                recruitBoard.getUser().getIntroduce(),
                recruitBoard.getUser().getProfileImg(),
                recruitBoard.getCreatedAt(),
                recruitBoard.getModifiedAt(),
                recruitBoard.getBookmarks().size(),
                recruitBoard.getReplies().size(),
                recruitBoard.getViews(),
                Long.parseLong(recruitBoard.getUser().getStudentId()),
                recruitBoard.getRequired(),
                recruitBoard.getOptional(),
                recruitBoard.getParty(),
                recruitBoard.isCompleted()
        );
    }

    public void setGathered(int gathered) {
        this.gathered = gathered;
    }

    public void setImage(List<ImageDto> image) {
        this.image = image;
    }

}