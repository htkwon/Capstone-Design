package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.Board;
import com.hansung.hansungcommunity.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FileRepository extends JpaRepository<FileEntity, Long> {

    List<FileEntity> findAllByBoard(Board board);

    FileEntity findByOriginalName(String name);


    void deleteByCreatedName(String imageName);
}
