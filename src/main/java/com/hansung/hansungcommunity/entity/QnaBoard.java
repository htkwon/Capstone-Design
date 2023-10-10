package com.hansung.hansungcommunity.entity;


import com.hansung.hansungcommunity.dto.qna.QnaBoardRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaBoard extends Board {

    @Id
    private Long id;
    private String language;
    @OneToOne(mappedBy = "qnaBoard", cascade = CascadeType.REMOVE)
    @JoinColumn(name = "adopt_id")
    private Adopt adopt;

    private QnaBoard(User user, String title, String content, String language) {
        super(title, content);
        super.setUser(user);
        this.language = language;
    }

    public static QnaBoard of(User user, String title, String content, String language) {
        return new QnaBoard(user, title, content, language);
    }

    public void patch(QnaBoardRequestDto dto) {
        updateTitleAndContent(dto.getTitle(), dto.getContent());

        this.language = dto.getLanguage();
    }

    public void setId(Long id) {
        this.id = id;
    }

}
