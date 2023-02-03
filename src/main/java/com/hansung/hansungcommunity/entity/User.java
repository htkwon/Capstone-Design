package com.hansung.hansungcommunity.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
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
    private List<FreeArticle> postFreeArticles = new ArrayList<>();
}
