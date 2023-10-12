package com.hansung.hansungcommunity.dto.recruit;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 소속 신청 정보를 담는 DTO
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecruitBoardApplyRequestDto {

    @NotNull
    private Boolean isMeetRequired; // 필수 조건 충족 여부
    private Boolean isMeetOptional; // 우대 조건 충족 여부

}
