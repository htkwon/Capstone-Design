package com.hansung.hansungcommunity.dto.recruit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hansung.hansungcommunity.dto.ImageDto;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecruitBoardListDto {

    private Long id;
    private String title;
    private String writer;
    private String profileImg;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int bookmark;
    private int reply;
    private int views;
    private Long stuId;
    // private List<String> imgUrl = new ArrayList<>();
    private String require;
    private String optional;
    private int party;
    private int gathered;
    @JsonProperty("isCompleted")
    private boolean isCompleted;

    private List<ImageDto> image;

    private RecruitBoardListDto(Long id, String title, String writer, String profileImg, LocalDateTime createdDate, LocalDateTime modifiedDate, int bookmark, int reply, int views, Long stuId, String require, String optional, int party, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.writer = writer;
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

}