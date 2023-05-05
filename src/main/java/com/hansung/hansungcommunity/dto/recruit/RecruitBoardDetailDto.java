package com.hansung.hansungcommunity.dto.recruit;

import com.hansung.hansungcommunity.entity.RecruitBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitBoardDetailDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    // private String profileImg;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int bookmark;
    // private int reply;
    private int views;
    private Long stuId;
    // private List<String> imgUrl = new ArrayList<>();
    private String require;
    private String optional;
    private int party;
    private int gathered;

    private RecruitBoardDetailDto(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate, int bookmark, int views, Long stuId, String require, String optional, int party) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.bookmark = bookmark;
        this.views = views;
        this.stuId = stuId;
        this.require = require;
        this.optional = optional;
        this.party = party;
    }

    public static RecruitBoardDetailDto from(RecruitBoard recruitBoard) {
        return new RecruitBoardDetailDto(
                recruitBoard.getId(),
                recruitBoard.getTitle(),
                recruitBoard.getContent(),
                recruitBoard.getUser().getNickname(),
                recruitBoard.getCreatedAt(),
                recruitBoard.getModifiedAt(),
                recruitBoard.getBookmarks().size(),
                recruitBoard.getViews(),
                Long.parseLong(recruitBoard.getUser().getStudentId()),
                recruitBoard.getRequired(),
                recruitBoard.getOptional(),
                recruitBoard.getParty()
        );
    }

}
