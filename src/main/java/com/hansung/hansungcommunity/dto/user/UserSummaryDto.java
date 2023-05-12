package com.hansung.hansungcommunity.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hansung.hansungcommunity.entity.Summary;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserSummaryDto {

    private Long summaryId;
    private String content;
    @JsonProperty("isFixed")
    private boolean isFixed;
    private LocalDateTime date;

    public UserSummaryDto(Long summaryId, String content, LocalDateTime date, boolean isFixed) {
        this.summaryId = summaryId;
        this.content = content;
        this.date = date;
        this.isFixed = isFixed;
    }

    public static UserSummaryDto of(Summary summary) {
        return new UserSummaryDto(
                summary.getId(),
                summary.getContent(),
                summary.getCreatedAt(),
                summary.isFixed()
        );
    }

}
