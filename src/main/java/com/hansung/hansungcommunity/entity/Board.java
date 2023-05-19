package com.hansung.hansungcommunity.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Board extends ModifiedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int views;

    @ManyToOne(fetch = FetchType.LAZY) // JPA 활용 시, XToOne 인 경우 fetch 타입을 LAZY 로 설정 !!!
    @JoinColumn(name = "stu_id")
    private User user;

    public void increaseViews() {
        this.views++;
    }

    public String getBoardType() {
        return this.getClass().getSimpleName(); // 게시글 타입은 엔티티 클래스의 이름(기본값)
    }

    /**
     * 추상 메소드, 하위 엔티티(FreeBoard 등)의 getTitle() 메소드에 의해 오버라이딩
     * 추후 공통 필드 리팩토링 시, 수정
     */
    public abstract String getTitle();

    public void setUser(User user) {
        this.user = user;
    }

}
