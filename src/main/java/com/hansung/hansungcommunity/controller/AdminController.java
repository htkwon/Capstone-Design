package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.dto.AdminBoardDto;
import com.hansung.hansungcommunity.dto.AdminUserDto;
import com.hansung.hansungcommunity.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    /**
     * 자유 게시글 조회
     */
    @GetMapping("/free")
    public ResponseEntity<List<AdminBoardDto>> getFreeList() {
        List<AdminBoardDto> list = adminService.getBoardList("free");
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /**
     * qna 게시글 조회
     */
    @GetMapping("/questions")
    public ResponseEntity<List<AdminBoardDto>> getQnaList() {
        List<AdminBoardDto> list = adminService.getBoardList("qna");
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /**
     * qna 게시글 조회
     */
    @GetMapping("/recruit")
    public ResponseEntity<List<AdminBoardDto>> getRecruitList() {
        List<AdminBoardDto> list = adminService.getBoardList("recruit");
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /**
     * 게시글 삭제
     */

    @DeleteMapping("/{boardType}/{boardId}")
    public ResponseEntity<Void> delete(@PathVariable("boardType") String boardType, @PathVariable("boardId") Long boardId) {
        adminService.deleteBoard(boardType, boardId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * user list 조회
     */
    @GetMapping("/user")
    public ResponseEntity<List<AdminUserDto>> getUserList() {
        List<AdminUserDto> list = adminService.getUserList();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    /**
     * user 삭제
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 유저가 관리자인지 체크
     */
    @GetMapping("/check")
    public ResponseEntity<Void> checkAdmin(Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


}
