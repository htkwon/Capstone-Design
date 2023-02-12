package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.dto.QnaBoardDto;
import com.hansung.hansungcommunity.dto.QnaBoardResponseDto;
import com.hansung.hansungcommunity.service.QnaBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class QnaBoardApiController {

    private final QnaBoardService qnaBoardService;

    /**
     * 게시글 리스트 조회 - 홈View전용 (게시글 4개 반환)
     */
    @GetMapping("qnaBoards")
    public ResponseEntity<List<QnaBoardResponseDto>> QnaList(){
        List<QnaBoardResponseDto> dtoList = qnaBoardService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }

    /**
     * 단건 QnaBoard 게시글 조회
     */
    @GetMapping("/qnaBoards/{boardId}")
    public QnaBoardDto article(@PathVariable Long boardId){
        return qnaBoardService.getBoard(boardId);
    }

    /**
     * 게시글 저장
     */
    @PostMapping("/qnaBoards/{userId}")
    public ResponseEntity<QnaBoardDto> create(@RequestBody QnaBoardDto dto, @PathVariable Long userId){
        QnaBoardDto articleDto = qnaBoardService.post(userId,dto);
        return ResponseEntity.status(HttpStatus.OK).body(articleDto);

    }

    //TODO: 나머지 api들은 Front 팀으로 부터 view 받고 testCode 작성 하면서 동시에 production code 개발 예정

}
