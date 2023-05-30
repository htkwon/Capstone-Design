package com.hansung.hansungcommunity.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Board extends ModifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    protected String title;
    @Lob
    protected String content;
    private int views;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    public List<Reply> replies = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) // JPA 활용 시, XToOne 인 경우 fetch 타입을 LAZY 로 설정 !!!
    @JoinColumn(name = "stu_id")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private Set<Bookmark> bookmarks = new HashSet<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<FileEntity> fileEntity = new ArrayList<>();

    public void increaseViews() {
        this.views++;
    }

    public String getBoardType() {
        return this.getClass().getSimpleName(); // 게시글 타입은 엔티티 클래스의 이름(기본값)
    }

    public void setUser(User user) {
        this.user = user;
    }

}
