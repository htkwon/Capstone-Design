package com.hansung.hansungcommunity.dto.recruit;

import lombok.Data;

/**
 * 소속 신청 정보를 담는 DTO
 */

@Data
public class RecruitBoardApplyRequestDto {

    private Boolean isMeetRequired; // 필수 조건 충족 여부
    private Boolean isMeetOptional; // 우대 조건 충족 여부

}
