package com.hansung.hansungcommunity.entity;

import com.hansung.hansungcommunity.repository.QnaArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("<QnaArticle> Entity 와 DB 테스트")
@Transactional
@SpringBootTest
class QnaArticleTest {
    /*
    단순 QnaArticle entity가 db와 연동 잘 되는지 확인 위한 TEST
    */

    @Autowired
    private QnaArticleRepository qnaArticleRepository;

    @DisplayName("QnaArticle - db 테스트")
    @Test
    void givenArticle_whenInserting_thenResultFine(){
        //Given
        QnaArticle article = QnaArticle.of(createUser(),"title","content","#test",10);

        //When
        QnaArticle result = qnaArticleRepository.save(article);

        //Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(article.getTitle());
    }


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



}