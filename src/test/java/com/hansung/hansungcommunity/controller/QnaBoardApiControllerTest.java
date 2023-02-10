package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.dto.QnaBoardDto;
import com.hansung.hansungcommunity.dto.UserDto;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.QnaBoardRepository;
import com.hansung.hansungcommunity.service.QnaBoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@DisplayName("QnaBoard Controller - 게시글")
@WebMvcTest(QnaBoardApiController.class)
class QnaBoardApiControllerTest {

    private final MockMvc mvc;

    @MockBean private QnaBoardService qnaBoardService;
    @Autowired
    private QnaBoardRepository qnaBoardRepository;

    QnaBoardApiControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("상세 게시글 호출 api")
    @Test
    void givenApiUrlWithBoardId_whenNothing_thenReturnsBoardJson() throws Exception {
        //Given
        Long boardId = 1L;
        BDDMockito.given(qnaBoardService.getBoard(boardId)).willReturn(createQnaBoardDto());
        //When

        //Then
        mvc.perform(MockMvcRequestBuilders.get("/api/qnaBoards/"+boardId))
                        .andExpect(MockMvcResultMatchers.status().isOk());
        //TODO: response되는 json형태의 data (ex..-> title,content 등등) test code 해야함
        BDDMockito.then(qnaBoardService).should().getBoard(boardId);
    }
    //TODO -> view 받고 개발 예정
    //TODO: QnA게시글 등록 Test code
    //TODO: QnA게시글 수정 test code
    //TODO: QnA게시글 삭제 test code
    //TODO: QnA게시글 전체 List반환 test code
    //TODO: QnA게시글 북마크 갱신 및 취소
    //TODO: QnA게시글 검색
    //TODO: QnA게시글 신고






    private QnaBoard createQnaBoard(){
        return QnaBoard.of(
                createUser(),
                "title",
                "content",
                "#spring",
                10
        );


    }

    private QnaBoardDto createQnaBoardDto(){
        return createQnaBoardDto(
                "title",
                "content",
                "tag",
                10
        );
    }


    //Test용 QnaBoard entity 생성
    private QnaBoardDto createQnaBoardDto(String title, String content, String tag, int point){
        return QnaBoardDto.of(
                1L,
                title,
                content,
                point,
                tag
        );
    }

    //Test용 User entity 생성
    private User createUser() {
        return User.of(
                "name",
                10,
                "nickname",
                "career",
                "introduce",
                null,
                null
        );
    }

    public UserDto createUserDto(){
        return UserDto.of(
                1L,
                "name",
                10,
                "nickname"
        );
    }

}