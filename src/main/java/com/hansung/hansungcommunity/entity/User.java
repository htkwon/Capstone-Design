package com.hansung.hansungcommunity.entity;


import com.hansung.hansungcommunity.dto.user.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stu_id")
    private Long id;

    private String studentId;
    private String name; // 이름
    private String nickname; // 닉네임
    private String career; // 경력
    private String introduce; // 소개글
    private String track1;
    private String track2;
    private String profileImg; // 프로필 사진, 추후 개발

    // private String todayComment; // 오늘의 한마디 (테이블 분리 ?)
    // private String skillStack; // 기술 스택, 추후 개발

    @OneToMany(mappedBy = "user")
    private List<FreeBoard> postFreeBoards = new ArrayList<>();

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "user")
    private List<QnaBoard> postQnaBoard = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RecruitBoard> postRecruitBoards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Party> parties = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<FreeBoardBookmark> freeBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<QnaBoardBookmark> qnaBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<FreeReply> freeReplies = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<QnaReply> qnaReplies = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Adopt> qnaAdopt = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_skill",
            joinColumns = {@JoinColumn(name = "stu_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    private Set<Skill> skills;

    @OneToMany(mappedBy = "user")
    private List<Summary> summaries = new ArrayList<>();


    private User(String studentId, String name, String nickname, String introduce, String track1, String track2, String profileImg) {
        this.studentId = studentId;
        this.name = name;
        this.nickname = nickname;
        this.introduce = introduce;
        this.track1 = track1;
        this.track2 = track2;
        this.profileImg = profileImg;
    }

    public static User from(UserRequestDto dto, Set<Skill> skills) {
        User user = new User(
                dto.getStudentId(),
                dto.getName(),
                dto.getNickname(),
                dto.getIntroduce(),
                dto.getTrack1(),
                dto.getTrack2(),
                dto.getPicture()
        );

        user.setSkills(skills);

        return user;
    }

    public void updateIntroduce(String introduce) {
        if (introduce != null) this.introduce = introduce;
    }

}
