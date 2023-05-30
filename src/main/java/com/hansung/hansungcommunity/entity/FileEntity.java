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

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "board")
    private Board board;

    @NotNull
    private String originalName;
    @NotNull
    private String createdName;

    public FileEntity(Board board, String originalName, String createdName) {
        this.board = board;
        this.originalName = originalName;
        this.createdName = createdName;
    }

    public static FileEntity of(Board board, String originalName, String createdName) {
        return new FileEntity(board, originalName, createdName);
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
