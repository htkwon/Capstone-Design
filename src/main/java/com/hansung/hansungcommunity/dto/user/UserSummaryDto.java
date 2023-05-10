package com.hansung.hansungcommunity.dto.user;

import com.hansung.hansungcommunity.entity.Summary;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserSummaryDto {

    private Long summaryId;

    private String content;

    private LocalDateTime date;


    public UserSummaryDto(Long summaryId, String content, LocalDateTime date) {
        this.summaryId = summaryId;
        this.content = content;
        this.date = date;
    }

    public static UserSummaryDto of(Summary summary){
        return new UserSummaryDto(
                summary.getId(),
                summary.getContent(),
                summary.getCreatedAt()
        );
    }
}
