package com.hansung.hansungcommunity.dto.user;

import com.hansung.hansungcommunity.entity.User;
import lombok.Data;

@Data
public class UserInfoDto {

    private Long id;
    private String studentId;
    private String nickname;
    private String name;
    private String track1;
    private String track2;
    private int point;
    private int board;
    private int reply;
    private int bookmark;
    private String selfIntroduction;

    //TODO
    /**
     * 이미지
     * 공부 내용 요약 기록
     * 기술 스택
     */


    public UserInfoDto(Long id, String studentId, String name, String nickname, String track1, String track2,int point,int board,int reply,int bookmark,String selfIntroduction) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.nickname = nickname;
        this.track1 = track1;
        this.track2 = track2;
        this.point = point;
        this.board = board;
        this.reply = reply;
        this.bookmark = bookmark;
        this.selfIntroduction = selfIntroduction;
    }


    public static UserInfoDto from(User user) {
        return new UserInfoDto(
                user.getId(),
                user.getStudentId(),
                user.getName(),
                user.getNickname(),
                user.getTrack1(),
                user.getTrack2(),
                user.getPoint(),
                user.getPostQnaBoard().size()+user.getPostFreeBoards().size(),
                user.getFreeReplies().size() + user.getQnaReplies().size(),
                user.getFreeBookmarks().size()+user.getQnaBookmarks().size(),
                user.getIntroduce()
        );
    }

}
