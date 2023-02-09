package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.QnaArticleDto;
import com.hansung.hansungcommunity.dto.UserDto;
import com.hansung.hansungcommunity.entity.QnaArticle;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.QnaArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("비지니스 로직 - Q&A게시글")
@ExtendWith(MockitoExtension.class)
class QnaArticleServiceTest {

    @InjectMocks private QnaArticleService sut;
    @Mock private QnaArticleRepository qnaArticleRepository;

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle(){
        //Given
        Long articleId = 1L;
        QnaArticle article = createQnaArticle();
        BDDMockito.given(qnaArticleRepository.findById(articleId)).willReturn(Optional.of(article));
        //When
        QnaArticleDto dto = sut.getArticle(articleId);
        //Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title",article.getTitle())
                .hasFieldOrPropertyWithValue("content",article.getContent())
                .hasFieldOrPropertyWithValue("tag",article.getTag())
                .hasFieldOrPropertyWithValue("point",article.getPoint());
        BDDMockito.then(qnaArticleRepository).should().findById(articleId);
    }
    @DisplayName("없는 게시글을 조회하면, 예외가 반환된다.")
    @Test
    void giveNonArticleId_whenSearchingArticle_thenReturnsException(){
        //Given
        Long articleId = 0L;
        BDDMockito.given(qnaArticleRepository.findById(articleId)).willReturn(Optional.empty());
        //When
        Throwable t = catchThrowable(()->sut.getArticle(articleId));
        //Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(articleId+" 인 게시글이 없습니다.");
        BDDMockito.then(qnaArticleRepository).should().findById(articleId);
    }
    /*
    @DisplayName("형식에 맞게 게시글 작성 후, 게시글을 생성한다.")
    @Test
    void givenArticleForm_whenSavingArticle_thenSavesArticle(){
        //Given
        Long userId = 100L;
        QnaArticlePostDto dto = createQnaArticleDto();
        BDDMockito.given(qnaArticleRepository.save(ArgumentMatchers.any(QnaArticle.class))).willReturn(createQnaArticle());
        //When
        sut.saveArticle(dto,userId);
        //Then
        BDDMockito.then(qnaArticleRepository).should().save(ArgumentMatchers.any(QnaArticle.class));
    }*/

    @DisplayName("형식에 맞게 게시글 수정 후, 게시글 수정한다.")
    @Test
    void givenModifiedArticle_whenUpdatingArticle_thenUpdatesArticle(){
        //Given
        QnaArticle article = createQnaArticle();
        QnaArticleDto dto = createQnaArticleDto("new title","new content","#newTag",10);
        BDDMockito.given(qnaArticleRepository.getReferenceById(dto.getId())).willReturn(article);
        //When
        sut.updateArticle(dto);
        //Then
        assertThat(article)
                .hasFieldOrPropertyWithValue("title",dto.getTitle())
                .hasFieldOrPropertyWithValue("content",dto.getContent())
                .hasFieldOrPropertyWithValue("tag",dto.getTag())
                .hasFieldOrPropertyWithValue("point",dto.getPoint());
        BDDMockito.then(qnaArticleRepository).should().getReferenceById(dto.getId());

    }
    @DisplayName("게시글의 id를 받고, 게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle(){
        //Given
        Long articleId = 1L;
        BDDMockito.willDoNothing().given(qnaArticleRepository).deleteById(articleId);
        //When
        sut.deleteArticle(1L);
        //Then
        BDDMockito.then(qnaArticleRepository).should().deleteById(articleId);
    }





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