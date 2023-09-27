package com.hansung.hansungcommunity.entity;


import com.hansung.hansungcommunity.dto.notice.NoticeBoardDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeBoard extends Board {

    @Id
    private Long id;

    public NoticeBoard(Long id, String title, String content, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        super.setUser(user);
    }

    public static NoticeBoard of(NoticeBoardDto dto, User user) {
        return new NoticeBoard(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                user
        );
    }

    // 비즈니스 메소드
    public void patch(NoticeBoardDto dto) {
        if (dto.getTitle() != null)
            this.title = dto.getTitle();

        if (dto.getContent() != null)
            this.content = dto.getContent();

        modified();
    }

    public void increaseHits() {
        increaseViews();
    }
    public void setId(Long id){this.id = id;}

}
