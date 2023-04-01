package com.hansung.hansungcommunity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.*;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.service.FileService;

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

    /**
     * 게시글 리스트 조회 - 홈View전용 (게시글 4개 반환)
     */
    @GetMapping("/qnaBoards")
    public ResponseEntity<Result<List<QnaBoardResponseDto>>> QnaList(){
        List<QnaBoardResponseDto> dtoList = qnaBoardService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(dtoList));
    }

    /**
     * 특정 게시글 조회
     * 조회수 증가 로직 구현을 위해 임의로 구현, 추후 수정
     */
    @GetMapping("/qnaBoards/{boardId}")
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
     * Qna 게시판 목록 페이지 (해당 페이지에 개수에 맞게 데이터 반환)
     * 페이지 정보는 프론트에서 전송
     */
    @GetMapping("/qnaBoardsPage")
    public ResponseEntity<List<QnaBoardListDto>> listOfPage(Pageable pageable){
        List<QnaBoardListDto> dtoList = qnaBoardService.findByPage(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }

    /**
     * 조회수가 높은 4개의 게시글 조회
     */
    @GetMapping("/qnaBoards/most")
    public ResponseEntity<List<MostViewedQnaBoardsDto>> mostViewed() {
        List<MostViewedQnaBoardsDto> mostViewedBoards = qnaBoardService.findMostViewedBoards();

        return ResponseEntity.status(HttpStatus.OK).body(mostViewedBoards);
    }

    /**
     * 게시글 저장 (업로드 파일 없을 때)
     */
    @PostMapping("/qnaBoardsNoFile")
    public ResponseEntity<QnaBoardDto> create(@RequestBody QnaBoardDto dto, Authentication authentication){
        CustomAuthentication ca = (CustomAuthentication) authentication;
        QnaBoardDto articleDto = qnaBoardService.post(ca.getUser().getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(articleDto);
    }

    /**
     * 게시글 저장 (업로드 파일 있을 때)
     */
    @PostMapping("/qnaBoards")
    public ResponseEntity<Object> create(
            MultipartFile[] multipartFiles,
            String stringQna,
            Authentication authentication
    ) {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        try {
            QnaBoard qnaBoard = new ObjectMapper().readValue(stringQna, QnaBoard.class);
            //objectMapper() 사용으로인해 자동으로 db에 등록되어, user 컬럼이 비어 있어 user 컬럼만 따로 매핑해주는 mappingUser사용
            qnaBoardService.mappingUser(ca.getUser().getId(), qnaBoard);

            for (MultipartFile file : multipartFiles) {
                String name = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt());
                String originalName = file.getOriginalFilename();
                String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

                assert originalName != null;
                String extension = getFilename(originalName, path);
                File save = new File(path, name + "." + extension);
                file.transferTo(save);

                FileDto dto = FileDto.of(qnaBoard, originalName, name, path);
                fileService.save(dto);


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    /**
     * 사진 url 전송 API
     */
    @PostMapping("/return/imageUrl")
    public String create2(MultipartFile[] multipartFiles) throws IOException {

            MultipartFile image = multipartFiles[0];

            String name = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt());
            String originalName = image.getOriginalFilename();
            String path = "C:\\images";

        assert originalName != null;
        String extension = getFilename(originalName,path);


            File save = new File(path, name + "." + extension);
            image.transferTo(save);
            return "/images/"+name+"."+extension;
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
            Cookie newCookie = new Cookie("qnaBoardHits","[" + boardId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }

    private String getFilename(String originalName,String path) {
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
