package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.dto.QnaArticleDto;
import com.hansung.hansungcommunity.dto.UserDto;
import com.hansung.hansungcommunity.entity.QnaArticle;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.QnaArticleRepository;
import com.hansung.hansungcommunity.service.QnaArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@DisplayName("QnaArticle Controller - 게시글")
@WebMvcTest(QnaArticleApiController.class)
class QnaArticleApiControllerTest {

    private final MockMvc mvc;

    @MockBean private QnaArticleService qnaArticleService;
    @Autowired
    private QnaArticleRepository qnaArticleRepository;

    QnaArticleApiControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("상세 게시글 호출 api")
    @Test
    void givenApiUrlWithArticleId_whenNothing_thenReturnsArticleJson() throws Exception {
        //Given
        Long articleId = 1L;
        BDDMockito.given(qnaArticleService.getArticle(articleId)).willReturn(createQnaArticleDto());
        //When

        //Then
        mvc.perform(MockMvcRequestBuilders.get("/api/qnaArticles/"+articleId))
                        .andExpect(MockMvcResultMatchers.status().isOk());
        //TODO: response되는 json형태의 data (ex..-> title,content 등등) test code 해야함
        BDDMockito.then(qnaArticleService).should().getArticle(articleId);
    }
    //TODO -> view 받고 개발 예정
    //TODO: QnA게시글 등록 Test code
    //TODO: QnA게시글 수정 test code
    //TODO: QnA게시글 삭제 test code
    //TODO: QnA게시글 전체 List반환 test code
    //TODO: QnA게시글 북마크 갱신 및 취소
    //TODO: QnA게시글 검색
    //TODO: QnA게시글 신고






    private QnaArticle createQnaArticle(){
        return QnaArticle.of(
                createUser(),
                "title",
                "content",
                "#spring",
                10
        );


    }

    private QnaArticleDto createQnaArticleDto(){
        return createQnaArticleDto(
                "title",
                "content",
                "tag",
                10
        );
    }


    //Test용 QnaArticle entity 생성
    private QnaArticleDto createQnaArticleDto(String title,String content,String tag,int point){
        return QnaArticleDto.of(
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