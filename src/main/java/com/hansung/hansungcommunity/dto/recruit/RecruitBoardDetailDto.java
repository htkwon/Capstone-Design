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
                recruitBoard.getParty(),
                recruitBoard.getGathered()
        );
    }

}
