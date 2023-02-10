package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.QnaBoardDto;
import com.hansung.hansungcommunity.dto.UserDto;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.QnaBoardRepository;
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
class QnaBoardServiceTest {

    @InjectMocks private QnaBoardService sut;
    @Mock private QnaBoardRepository qnaBoardRepository;

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenBoardId_whenSearchingBoard_thenReturnsBoard(){
        //Given
        Long boardId = 1L;
        QnaBoard board = createQnaBoard();
        BDDMockito.given(qnaBoardRepository.findById(boardId)).willReturn(Optional.of(board));
        //When
        QnaBoardDto dto = sut.getBoard(boardId);
        //Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title",board.getTitle())
                .hasFieldOrPropertyWithValue("content",board.getContent())
                .hasFieldOrPropertyWithValue("tag",board.getTag())
                .hasFieldOrPropertyWithValue("point",board.getPoint());
        BDDMockito.then(qnaBoardRepository).should().findById(boardId);
    }
    @DisplayName("없는 게시글을 조회하면, 예외가 반환된다.")
    @Test
    void giveNonBoardId_whenSearchingBoard_thenReturnsException(){
        //Given
        Long boardId = 0L;
        BDDMockito.given(qnaBoardRepository.findById(boardId)).willReturn(Optional.empty());
        //When
        Throwable t = catchThrowable(()->sut.getBoard(boardId));
        //Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(boardId+" 인 게시글이 없습니다.");
        BDDMockito.then(qnaBoardRepository).should().findById(boardId);
    }
    /*

    @DisplayName("형식에 맞게 게시글 작성 후, 게시글을 생성한다.")
    @Test
    void givenBoardForm_whenSavingBoard_thenSavesBoard(){
        //Given
        Long userId = 100L;
        QnaBoardPostDto dto = createQnaBoardDto();
        BDDMockito.given(qnaBoardRepository.save(ArgumentMatchers.any(QnaBoard.class))).willReturn(createQnaBoard());
        //When
        sut.saveBoard(dto,userId);
        //Then
        BDDMockito.then(qnaBoardRepository).should().save(ArgumentMatchers.any(QnaBoard.class));
    }*/

    @DisplayName("형식에 맞게 게시글 수정 후, 게시글 수정한다.")
    @Test
    void givenModifiedBoard_whenUpdatingBoard_thenUpdatesBoard(){
        //Given
        QnaBoard board = createQnaBoard();
        QnaBoardDto dto = createQnaBoardDto("new title","new content","#newTag",10);
        BDDMockito.given(qnaBoardRepository.getReferenceById(dto.getId())).willReturn(board);
        //When
        sut.update(dto);
        //Then
        assertThat(board)
                .hasFieldOrPropertyWithValue("title",dto.getTitle())
                .hasFieldOrPropertyWithValue("content",dto.getContent())
                .hasFieldOrPropertyWithValue("tag",dto.getTag())
                .hasFieldOrPropertyWithValue("point",dto.getPoint());
        BDDMockito.then(qnaBoardRepository).should().getReferenceById(dto.getId());

    }
    @DisplayName("게시글의 id를 받고, 게시글을 삭제한다.")
    @Test
    void givenBoardId_whenDeletingBoard_thenDeletesBoard(){
        //Given
        Long boardId = 1L;
        BDDMockito.willDoNothing().given(qnaBoardRepository).deleteById(boardId);
        //When
        sut.delete(1L);
        //Then
        BDDMockito.then(qnaBoardRepository).should().deleteById(boardId);
    }





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