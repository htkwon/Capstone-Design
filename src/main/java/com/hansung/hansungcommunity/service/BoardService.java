package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.common.BoardMainDto;
import com.hansung.hansungcommunity.entity.Board;
import com.hansung.hansungcommunity.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardMainDto> getPopularBoards() {
        LocalDateTime standardTime = LocalDateTime.now().minusWeeks(1); // 1주일 기준
        List<Board> popularBoards = boardRepository.getPopularBoards(standardTime, PageRequest.of(0, 5)); // 상위 5개의 게시글

        return popularBoards.stream().map(BoardMainDto::from).collect(Collectors.toList());
    }

}
