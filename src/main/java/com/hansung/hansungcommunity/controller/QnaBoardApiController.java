package com.hansung.hansungcommunity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.FileDto;
import com.hansung.hansungcommunity.dto.qna.*;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.service.FileService;
import com.hansung.hansungcommunity.service.FireBaseService;
import com.hansung.hansungcommunity.service.QnaBoardService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.multi.MultiOptionPaneUI;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class QnaBoardApiController {

    private final QnaBoardService qnaBoardService;
    private final FileService fileService;
    private final FireBaseService fireBaseService;

    /**
     * 게시글 리스트 조회 - 홈 View 전용 (게시글 4개 반환)
     */
    @GetMapping("/questions/main")
    public ResponseEntity<Result<List<QnaBoardMainDto>>> QnaList() {
        List<QnaBoardMainDto> dtoList = qnaBoardService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(dtoList));
    }

    /**
     * 특정 게시글 조회
     * 조회수 증가 로직 구현을 위해 임의로 구현, 추후 수정
     */
    @GetMapping("/questions/detail/{boardId}")
    public ResponseEntity<QnaBoardDetailsDto> detail(
            @PathVariable("boardId") Long boardId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        increaseHits(boardId, request, response);
        QnaBoardDetailsDto boardDto = qnaBoardService.findOne(boardId);

        return ResponseEntity.status(HttpStatus.OK).body(boardDto);
    }

    /**
     * 게시글 수정 시, 게시글 상세 정보 조회
     */
    @GetMapping("/questions/update/{boardId}")
    public ResponseEntity<QnaBoardUpdateDto> detailForUpdate(@PathVariable("boardId") Long boardId) {
        QnaBoardUpdateDto boardDto = qnaBoardService.findOneForUpdate(boardId);

        return ResponseEntity.ok(boardDto);
    }

    /**
     * Qna 게시판 목록 페이지 (해당 페이지에 개수에 맞게 데이터 반환)
     * 페이지 정보는 프론트에서 전송
     */
    @GetMapping("/questions/list")
    public ResponseEntity<List<QnaBoardListDto>> listOfPage(Pageable pageable, @RequestParam(required = false) String search) {
        List<QnaBoardListDto> dtoList = qnaBoardService.findByPage(pageable, search);

        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }

    /**
     * 조회수가 높은 4개의 게시글 조회
     */
    @GetMapping("/questions/most")
    public ResponseEntity<List<QnaBoardMostViewedDto>> mostViewed() {
        List<QnaBoardMostViewedDto> mostViewedBoards = qnaBoardService.findMostViewedBoards();

        return ResponseEntity.status(HttpStatus.OK).body(mostViewedBoards);
    }

    /**
     * 게시글 저장 (업로드 파일 없을 때)
     */
    @PostMapping("/questions/no-file")
    public ResponseEntity<Long> create(@RequestBody QnaBoardRequestDto dto, Authentication authentication) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        Long savedId = qnaBoardService.post(ca.getUser().getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(savedId);
    }

    /**
     *
     * 게시글 저장 (업로드 파일 있을 때)
     */

    @PostMapping("/questions")
    public ResponseEntity<Long> create(
            @RequestParam("file") MultipartFile[] file,
            String stringQna,
            Authentication authentication
    ) throws IOException, FirebaseAuthException {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        QnaBoard board = new ObjectMapper().readValue(stringQna, QnaBoard.class);
        Long id = qnaBoardService.mappingUser(ca.getUser().getId(),board);

        for(MultipartFile f : file){
                String fileName = f.getOriginalFilename();
                FileDto dto = FileDto.of(board,fileName);
                fileService.save(dto);
                fireBaseService.uploadFiles(f,fileName);
        }
        return ResponseEntity.status(HttpStatus.OK).body(id);

    }

    /**
     * 게시글 수정
     */
    @PutMapping("/questions/update/{boardId}")
    public ResponseEntity<Long> update(
            @PathVariable("boardId") Long boardId,
            @RequestBody QnaBoardRequestDto dto
    ) {
        Long id = qnaBoardService.update(boardId, dto);

        return ResponseEntity.ok(id);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/questions/delete/{boardId}")
    public ResponseEntity<Long> delete(@PathVariable("boardId") Long boardId) {
        qnaBoardService.delete(boardId);

        return ResponseEntity.ok(boardId);
    }

    /**
     * 전체 게시글 수
     */
    @GetMapping("/questions/total")
    public ResponseEntity<Long> getTotal() {
        return ResponseEntity.ok(qnaBoardService.getTotal());
    }

    /**
     * 사진 url 전송 API
     */
    @PostMapping("/questions/image")
    public String create2(MultipartFile[] multipartFiles) throws IOException {

        MultipartFile image = multipartFiles[0];

        String name = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt());
        String originalName = image.getOriginalFilename();
        String path = "C:\\images";

        assert originalName != null;
        String extension = getFilename(originalName, path);


        File save = new File(path, name + "." + extension);
        image.transferTo(save);
        return "/images/" + name + "." + extension;
    }

    @GetMapping("/questions/return/user-id/{boardId}")
    public ResponseEntity<Long> getUserId(@PathVariable("boardId") Long boardId) {
        Long userId = qnaBoardService.getUserId(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    /**
     * 조회수 증가 로직
     */
    private void increaseHits(Long boardId, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("qnaBoardHits")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + boardId.toString() + "]")) {
                qnaBoardService.increaseHits(boardId);
                oldCookie.setValue(oldCookie.getValue() + "_[" + boardId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            qnaBoardService.increaseHits(boardId);
            Cookie newCookie = new Cookie("qnaBoardHits", "[" + boardId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }

    private String getFilename(String originalName, String path) {
        String extension = originalName.substring(originalName.lastIndexOf(".") + 1);
        if (!new File(path).exists()) {
            try {
                new File(path).mkdir();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return extension;

    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

}
