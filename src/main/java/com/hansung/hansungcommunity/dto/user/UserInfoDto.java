package com.hansung.hansungcommunity.dto.user;

import com.hansung.hansungcommunity.entity.Skill;
import com.hansung.hansungcommunity.entity.User;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserInfoDto {

    private Long id;
    private String studentId;
    private String nickname;
    private String name;
    private String track1;
    private String track2;
    private int board;
    private int application;
    private int bookmark;
    private String introduce;
    private String profileImg;
    private Set<String> skills; // 관심 기술

    public UserInfoDto(Long id, String studentId, String name, String nickname, String track1, String track2, int board, int bookmark, String introduce, String profileImg, Set<Skill> skills) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.nickname = nickname;
        this.track1 = track1;
        this.track2 = track2;
        this.board = board;
        this.bookmark = bookmark;
        this.introduce = introduce;
        this.profileImg = profileImg;
        this.skills = skills.stream().map(Skill::getName).collect(Collectors.toSet());
    }

    public static UserInfoDto from(User user) {
        return new UserInfoDto(
                user.getId(),
                user.getStudentId(),
                user.getName(),
                user.getNickname(),
                user.getTrack1(),
                user.getTrack2(),
                user.getPostBoards().size(),
                user.getBookmarks().size(),
                user.getIntroduce(),
                user.getProfileImg(),
                user.getSkills()
        );
    }

}
