package com.hansung.hansungcommunity.service;


import com.hansung.hansungcommunity.dto.ImageDto;
import com.hansung.hansungcommunity.dto.qna.*;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.exception.BoardNotFoundException;
import com.hansung.hansungcommunity.repository.QnaBoardRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QnaBoardService {

    private final UserRepository userRepository;
    private final QnaBoardRepository qnaBoardRepository;

    /**
     * 특정 게시글 조회
     */
    public QnaBoardDetailsDto findOne(Long boardId) {
        QnaBoard board = qnaBoardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글 조회 실패, 해당하는 게시글이 없습니다."));

        return new QnaBoardDetailsDto(board);
    }

    /**
     * 정렬 된 4개 Qna 게시글 반환
     */
    public List<QnaBoardMainDto> findAll() {
        Pageable pageable = PageRequest.of(0, 4, Sort.Direction.DESC, "createdAt");

        return qnaBoardRepository.findAll(pageable).getContent()
                .stream()
                .map(QnaBoardMainDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 조회수를 기준으로 4개의 게시글 조회
     */
    public List<QnaBoardMostViewedDto> findMostViewedBoards() {
        Pageable pageable = PageRequest.of(0, 4, Sort.Direction.DESC, "views");
        LocalDateTime standardTime = LocalDateTime.now().minusWeeks(1); // 1주일 기준

        return qnaBoardRepository.findByCreatedAtAfter(standardTime, pageable)
                .stream()
                .map(QnaBoardMostViewedDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 게시글 저장
     */
    @Transactional
    public Long post(Long userId, QnaBoardRequestDto dto) {
        User user = userRepository.getReferenceById(userId);
        QnaBoard board = dto.toEntity();
        board.setUser(user);

        QnaBoard savedBoard = qnaBoardRepository.save(board);

        return savedBoard.getId();
    }

    /**
     * user 컬럼만 비어있는 qnaBoard 엔티티에 유저 매핑
     *
     * @return
     */
    @Transactional
    public Long mappingUser(Long userId, QnaBoard entity) {
        User user = userRepository.getReferenceById(userId);

        entity.setUser(user);
        qnaBoardRepository.save(entity);
        return entity.getId();
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Long update(Long boardId, QnaBoardRequestDto dto) {
        QnaBoard target = qnaBoardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글 수정 실패, 해당하는 게시글이 없습니다."));

        target.updateBoard(dto);

        return boardId;
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void delete(Long boardId) {
        qnaBoardRepository.deleteById(boardId);
    }

    /**
     * 조회수 증가 로직
     * Auditing 수정 시간 업데이트, 논의 후 해결 요망
     */
    @Transactional
    public void increaseHits(Long boardId) {
        QnaBoard board = qnaBoardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("조회수 증가 실패, 해당하는 게시글이 없습니다."));

        board.increaseHits();
    }

    /**
     * 게시글 리스트 조회
     * 프론트에서 요청한 페이지 정보에 맞게 게시글 반환
     */
    public List<QnaBoardListDto> findByPage(Pageable pageable, String search) {
        Pageable setPage = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "createdAt");
        Page<QnaBoard> page;

        if (search == null) {
            page = qnaBoardRepository.findAll(setPage);
        } else {
            page = qnaBoardRepository.findAllWithSearchParam(search, setPage);
        }

        return page.getContent()
                .stream()
                .map(board -> {
                    QnaBoardListDto dto = new QnaBoardListDto(board);
                    dto.setImage(extractImagesFromContent(board.getContent()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // TODO 해당 메소드, 해당 메소드를 호출하는 핸들러 메소드의 필요성 논의 필요
    public Long getUserId(Long boardId) {
        QnaBoard qnaBoard = qnaBoardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("해당하는 게시글이 없습니다."));

        return qnaBoard.getUser().getId();
    }

    /**
     * 게시글 수정 시, 기존 게시글 정보 반환
     */
    public QnaBoardUpdateDto findOneForUpdate(Long boardId) {
        QnaBoard board = qnaBoardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글 조회 실패, 해당하는 게시글이 없음"));

        return new QnaBoardUpdateDto(board);
    }

    /**
     * 전체 게시글 수
     */
    public long getTotal() {
        return qnaBoardRepository.count();
    }

    /**
     * 이미지 추출
     */
    private List<ImageDto> extractImagesFromContent(String content) {
        List<ImageDto> images = new ArrayList<>();

        // 정규표현식 패턴
        String patternString = "<img\\s+[^>]*src\\s*=\\s*\"([^\"]*)\"[^>]*>";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(content);

        // 매칭된 이미지 URL 추출
        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            ImageDto imageDto = new ImageDto();
            imageDto.setImageUrl(imageUrl);
            images.add(imageDto);
        }

        return images;
    }

}
