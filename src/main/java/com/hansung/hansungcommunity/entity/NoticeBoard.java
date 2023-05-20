package com.hansung.hansungcommunity.entity;


import com.hansung.hansungcommunity.dto.NoticeBoardDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(callSuper = true)
@Entity
@NoArgsConstructor
@Setter

public class NoticeBoard extends Board {
    @Id
    private Long id;
    @NotNull
    private String title;
    @NotNull
    @Lob
    private String content;




    public NoticeBoard(Long id, String title, String content,User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        super.setUser(user);
    }


    public static NoticeBoard of(NoticeBoardDto dto,User user){
        return new NoticeBoard(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                user
        );
    }

//    public static NoticeBoard createBoard(User user,NoticeBoardDto dto){
//        NoticeBoard board = new NoticeBoard();
//        board.setUser(user);
//        board.setTitle(dto.getTitle());
//        board.setContent(dto.getContent());
//        return board;
//    }
//
//    public void setUser(User user) {
//        super.setUser(user);
//        user.getPostNoticeBoards().add(this); // 필요한가?
//    }

}
