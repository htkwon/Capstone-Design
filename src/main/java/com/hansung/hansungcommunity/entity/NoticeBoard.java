package com.hansung.hansungcommunity.entity;


import com.hansung.hansungcommunity.dto.NoticeBoardDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(callSuper = true)
@Entity
@NoArgsConstructor
@Setter
public class NoticeBoard extends Board {

    @Id
    private Long id;
    @OneToMany(mappedBy = "noticeBoard", cascade = CascadeType.REMOVE)
    public List<NoticeReply> replies = new ArrayList<>();

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

}
