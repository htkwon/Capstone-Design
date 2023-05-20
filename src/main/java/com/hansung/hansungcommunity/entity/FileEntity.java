package com.hansung.hansungcommunity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.aspectj.weaver.ast.Not;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@ToString(callSuper = true)
@Entity
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board")
    private Board board;

    @NotNull private String originalName;

    public FileEntity(Board board, String originalName) {
        this.board = board;
        this.originalName = originalName;
    }

    public static FileEntity of(Board board, String originalName){
        return new FileEntity(board,originalName);
    }

    public void setBoard(Board board){this.board = board;}
}
