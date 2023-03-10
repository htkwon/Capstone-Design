package com.hansung.hansungcommunity.entity;

import com.hansung.hansungcommunity.repository.QnaBoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("<QnaBoard> Entity 와 DB 테스트")
@Transactional
@SpringBootTest
class QnaBoardTest {
    /*
    단순 QnaBoard entity가 db와 연동 잘 되는지 확인 위한 TEST
    */

    @Autowired
    private QnaBoardRepository qnaBoardRepository;

    @DisplayName("QnaBoard - db 테스트")
    @Test
    void givenBoard_whenInserting_thenResultFine(){
        //Given
        QnaBoard board = QnaBoard.of(createUser(),"title","content","#test",10,"c");

        //When
        QnaBoard result = qnaBoardRepository.save(board);


        //Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(board.getTitle());
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