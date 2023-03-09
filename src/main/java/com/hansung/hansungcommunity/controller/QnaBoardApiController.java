package com.hansung.hansungcommunity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hansung.hansungcommunity.dto.*;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.service.ImageService;
import com.hansung.hansungcommunity.service.QnaBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final ImageService imageService;

    /**
     * 게시글 리스트 조회 - 홈View전용 (게시글 4개 반환)
     */
    @GetMapping("qnaBoards")
    public ResponseEntity<List<QnaBoardResponseDto>> QnaList(){
        List<QnaBoardResponseDto> dtoList = qnaBoardService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }

    /**
     * 게시글 리스트 조회
     */
    @GetMapping("/qnaBoards/list")
    public ResponseEntity<List<QnaListResponseDto>> list() {
        List<QnaListResponseDto> dtoList = qnaBoardService.list();
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
     * 단건 QnaBoard 게시글 조회
     */
    @GetMapping("/qnaBoards/{boardId}")
    public QnaBoardDto article(@PathVariable Long boardId){
        return qnaBoardService.getBoard(boardId);
    }

    /**
     * 게시글 저장
     */
    @PostMapping("/qnaBoardsNoImage/{userId}")
    public ResponseEntity<QnaBoardDto> create(@RequestBody QnaBoardDto dto, @PathVariable Long userId){
        System.out.println(dto.getPoint());

        QnaBoardDto articleDto = qnaBoardService.post(userId,dto);
        return ResponseEntity.status(HttpStatus.OK).body(articleDto);

    }

    /**
     *게시글 저장 (업로드 이미지 있을 때)
     */
    @PostMapping("/qnaBoards/{userId}")
    public ResponseEntity<Object> create(MultipartFile[] multipartFiles,String stringQna,@PathVariable Long userId){

        try {
            QnaBoard qnaBoard = new ObjectMapper().readValue(stringQna, QnaBoard.class);
            //objectMapper() 사용으로인해 자동으로 db에 등록되어, user 컬럼이 비어 있어 user 컬럼만 따로 매핑해주는 mappingUser사용
            qnaBoardService.mappingUser(userId,qnaBoard);

                for (int i = 0; i < multipartFiles.length; i++) {
                    MultipartFile image = multipartFiles[i];

                    String name = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt());
                    String originalName = image.getOriginalFilename();
                    String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images";

                    String extension = originalName.substring(originalName.lastIndexOf(".") + 1);
                    if (!new File(path).exists()) {
                        try {
                            new File(path).mkdir();
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                    File save = new File(path, name + "." + extension);
                    image.transferTo(save);

                    ImageDto dto = ImageDto.of(qnaBoard, originalName, name, path);
                    imageService.save(dto);
                }
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        return new ResponseEntity<Object>("Success",HttpStatus.OK);
    }

    @PostMapping("/return/imageUrl")
    public String create2(MultipartFile[] multipartFiles) throws IOException {

            MultipartFile image = multipartFiles[0];

            String name = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt());
            String originalName = image.getOriginalFilename();
            String path = "C:\\images";
            String extension = originalName.substring(originalName.lastIndexOf(".") + 1);
            if (!new File(path).exists()) {
                try {
                    new File(path).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            File save = new File(path, name + "." + extension);
            image.transferTo(save);

            return "/images/"+name+"."+extension;
    }


}
