package com.hansung.hansungcommunity.entity;


import com.hansung.hansungcommunity.dto.notice.NoticeBoardDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeBoard extends Board {

    @Id
    private Long id;

    private NoticeBoard(String title, String content, User user) {
        super(title, content);
        super.setUser(user);
    }

    public static NoticeBoard of(NoticeBoardDto dto, User user) {
        return new NoticeBoard(
                dto.getTitle(),
                dto.getContent(),
                user
        );
    }

    // 비즈니스 메소드
    public void patch(NoticeBoardDto dto) {
        updateTitleAndContent(dto.getTitle(), dto.getContent());
    }

    public void setId(Long id) {
        this.id = id;
    }

}
