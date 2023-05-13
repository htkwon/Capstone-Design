package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.user.UserSummaryDto;
import com.hansung.hansungcommunity.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SummaryController {
    private final SummaryService summaryService;

    /**
     * 오늘의 한줄 요약 작성
     */
    @PostMapping("/user/summary/mypage")
    public ResponseEntity<UserSummaryDto> postSummary(@RequestBody UserSummaryDto dto, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        UserSummaryDto summaryDto = summaryService.postSummary(ca.getUser().getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(summaryDto);
    }

    /**
     * 오늘의 한줄 요약 조회
     */
    @GetMapping("/user/summary/mypage")
    public ResponseEntity<List<UserSummaryDto>> getSummary(Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        List<UserSummaryDto> dtos = summaryService.getSummary(ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /**
     * 고정된 오늘의 한줄 요약 조회
     */
    @GetMapping("/user/summary/mypage/fixed")
    public ResponseEntity<List<UserSummaryDto>> getFixedSummary(Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        List<UserSummaryDto> dtos = summaryService.getFixedSummary(ca.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /**
     * 오늘의 한줄 요약 삭제
     */
    @DeleteMapping("/user/{summaryId}/summary/mypage")
    public ResponseEntity<Void> deleteSummary(@PathVariable("summaryId") Long summaryId) {
        summaryService.deleteSummary(summaryId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 오늘의 한줄 요약 수정
     */
    @PutMapping("/user/{summaryId}/summary/mypage")
    public ResponseEntity<UserSummaryDto> updateSummary(@PathVariable("summaryId") Long summaryId, @RequestBody UserSummaryDto dto) {
        UserSummaryDto summaryDto = summaryService.updateSummary(summaryId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(summaryDto);
    }

    /**
     * 오늘의 한줄 고정
     */
    @PutMapping("/user/{summaryId}/summary/fix")
    public ResponseEntity<Void> fixSummary(@PathVariable("summaryId") Long summaryId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        summaryService.fixSummary(summaryId, ca.getUser().getId());

        return ResponseEntity.ok().build();
    }

    /**
     * 오늘의 한줄 고정 해제
     */
    @PutMapping("/user/{summaryId}/summary/release")
    public ResponseEntity<Void> releaseSummary(@PathVariable("summaryId") Long summaryId, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        summaryService.releaseSummary(summaryId, ca.getUser().getId());

        return ResponseEntity.ok().build();
    }

}
