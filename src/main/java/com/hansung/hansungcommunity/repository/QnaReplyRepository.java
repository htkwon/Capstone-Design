package com.hansung.hansungcommunity.repository;

import com.hansung.hansungcommunity.entity.QnaReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaReplyRepository extends JpaRepository<QnaReply, Long> {


    List<QnaReply> findAllByParentId(Long id);

}
