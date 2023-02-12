package com.hansung.hansungcommunity.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stu_id")
    private Long id;
    private String name; // 이름
    private int point; // 포인트
    private String nickname; // 닉네임
    private String career; // 경력
    private String introduce; // 소개글

    // private String todayComment; // 오늘의 한마디 (테이블 분리 ?)
    // private Image profileImage // 프로필 사진, 추후 개발
    // private String skillStack; // 기술 스택, 추후 개발


    // 일대다, 필요한가?
    @OneToMany(mappedBy = "user")
    private List<FreeBoard> postFreeBoards = new ArrayList<>();

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "user")
    private List<QnaBoard> postQnaBoard = new ArrayList<>();


    public User(){}

    public User(String name, int point, String nickname, String career, String introduce, List<FreeArticle> postFreeArticles, List<QnaBoard> postQnaBoards) {
        this.name = name;
        this.point = point;
        this.nickname = nickname;
        this.career = career;
        this.introduce = introduce;
        this.postFreeArticles = postFreeArticles;
    }

    public static User of(String name, int point, String nickname, String career, String introduce, List<FreeArticle> postFreeArticles, List<QnaBoard> postQnaBoards){
    }
}
