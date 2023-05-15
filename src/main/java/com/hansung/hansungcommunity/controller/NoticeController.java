package com.hansung.hansungcommunity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.FileDto;
import com.hansung.hansungcommunity.dto.NoticeBoardDto;
import com.hansung.hansungcommunity.entity.NoticeBoard;
import com.hansung.hansungcommunity.service.FileService;
import com.hansung.hansungcommunity.service.NoticeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.springframework.util.StringUtils.getFilename;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NoticeController {

    private final NoticeService noticeService;
    private final FileService fileService;

    /**
     * 전체 글 조회
     */
    @GetMapping("/notice")
    public ResponseEntity<List<NoticeBoardDto>> getList(Authentication authentication){
        CustomAuthentication ca = (CustomAuthentication) authentication;
        List<NoticeBoardDto> dtos = noticeService.getList(ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /**
     * 글 상세보기
     */
    @GetMapping("/notice/{boardId}/detail")
    public ResponseEntity<NoticeBoardDto> detail(@PathVariable("boardId") Long boardId){
        NoticeBoardDto dto = noticeService.detail(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /**
     * 글 작성 (업로드 파일 없을 때)
     */
    @PostMapping("/notice")
    public ResponseEntity<NoticeBoardDto> post(@RequestBody NoticeBoardDto dto, Authentication authentication){
        CustomAuthentication ca = (CustomAuthentication) authentication;
        NoticeBoardDto noticeBoardDto = noticeService.post(dto,ca.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(noticeBoardDto);

    }
    /**
     * 글 작성(업로드 파일 있을 때)
     */
//    @PostMapping("/notice/file")
//    public ResponseEntity<Object> create(
//            MultipartFile[] multipartFiles,
//            String stringNotice,
//            Authentication authentication
//    ){
//        CustomAuthentication ca = (CustomAuthentication) authentication;
//        try {
//            NoticeBoard noticeBoard = new ObjectMapper().readValue(stringNotice,NoticeBoard.class);
//            noticeService.mappingUser(ca.getUser().getId(),noticeBoard);
//
//            for(MultipartFile file : multipartFiles){
//                String name = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt());
//                String originalName = file.getOriginalFilename();
//                String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
//
//                assert originalName != null;
//                String extension = getFilename(originalName, path);
//                File save = new File(path, name + "." + extension);
//                file.transferTo(save);
//
//                FileDto dto = FileDto.of(noticeBoard,originalName,name,path);
//                fileService.save(dto);
//
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return new ResponseEntity<>("Success",HttpStatus.OK);
//    }

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


    /**
     * 글 수정
     */

    @PutMapping("/notice/{boardId}/update")
    public ResponseEntity<NoticeBoardDto> update(@RequestBody NoticeBoardDto dto, @PathVariable("boardId") Long boardId){
        NoticeBoardDto noticeBoardDto = noticeService.update(dto,boardId);
        return ResponseEntity.status(HttpStatus.OK).body(noticeBoardDto);
    }


    /**
     * 글 삭제
     */
    @DeleteMapping("/notice/{boardId}/delete")
    public ResponseEntity<Void> delete(@PathVariable("boardId") Long boardId){
        noticeService.delete(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }




}
