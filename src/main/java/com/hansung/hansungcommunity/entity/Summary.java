package com.hansung.hansungcommunity.entity;

import com.hansung.hansungcommunity.dto.user.UserSummaryDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Summary extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private boolean isFixed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    public Summary(Long summaryId, String content, boolean isFixed) {
        this.id = summaryId;
        this.content = content;
        this.isFixed = isFixed;
    }

    public static Summary of(UserSummaryDto dto) {
        return new Summary(
                dto.getSummaryId(),
                dto.getContent(),
                false
        );
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    // 고정
    public void fix() {
        this.isFixed = true;
    }

    // 고정 해제
    public void release() {
        this.isFixed = false;
    }

}
