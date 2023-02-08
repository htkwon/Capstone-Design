package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.FreeArticleDto;
import com.hansung.hansungcommunity.dto.FreeArticleResponseDto;
import com.hansung.hansungcommunity.entity.FreeArticle;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.FreeArticleRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // 생성자 주입 (final 키워드)
@Transactional(readOnly = true) // 읽기 전용
public class FreeArticleService {
    private final FreeArticleRepository freeArticleRepository;
    private final UserRepository userRepository;

    /**
     * 자유 게시글 게시
     */
    @Transactional // 필요 시 쓰기 전용
    public FreeArticleDto post(Long userId, FreeArticleDto articleDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 게시 실패, 해당하는 유저가 없음"));

        FreeArticle article = FreeArticle.createArticle(user, articleDto); // 게시글 생성

        FreeArticle savedArticle = freeArticleRepository.save(article); // DB에 저장

        return new FreeArticleDto(savedArticle); // DTO 변환 후 반환
    }

    /**
     * 자유 게시글 수정
     */
    @Transactional
    public FreeArticleDto update(Long articleId, FreeArticleDto dto) {
        FreeArticle target = freeArticleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 수정 실패, 해당하는 게시글이 없음"));

        // 게시글 수정
        target.patch(dto);

        return dto;
    }

    /**
     * 자유 게시글 삭제
     */
    @Transactional
    public FreeArticleDto delete(Long articleId) {
        FreeArticle target = freeArticleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 삭제 실패, 해당하는 게시글이 없음"));

        // 게시글 삭제
        freeArticleRepository.delete(target);

        return new FreeArticleDto(target);
    }

    /**
     * 게시글 리스트 조회
     * 정렬 후, 4개의 게시글만 반환
     */
    public List<FreeArticleResponseDto> findAll() {
        List<FreeArticle> list = freeArticleRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream().limit(4).collect(Collectors.toList());

        return list.stream()
                .map(FreeArticleResponseDto::new)
                .collect(Collectors.toList());
    }
}

