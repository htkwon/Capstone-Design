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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    private String content;

    public Summary(Long summaryId, String content) {
        this.id = summaryId;
        this.content = content;
    }

    public static Summary of(UserSummaryDto dto) {
        return new Summary(
                dto.getSummaryId(),
                dto.getContent()
        );
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateContent(String content) {
        this.content = content;
    }

}
