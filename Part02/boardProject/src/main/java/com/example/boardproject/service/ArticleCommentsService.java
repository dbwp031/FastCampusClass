package com.example.boardproject.service;

import com.example.boardproject.dto.ArticleCommentDto;
import com.example.boardproject.repository.ArticleCommentRepository;
import com.example.boardproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentsService {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return articleCommentRepository.findByArticle_Id(articleId)
                .stream()
                .map(ArticleCommentDto::from)
                .toList();
    }

    public void saveArticleComment(ArticleCommentDto dto) {
    }
    public void updateArticleComment(ArticleCommentDto dto) {
    }

    public void deleteArticleComment(Long articleCommentId) {
        articleCommentRepository.deleteById(articleCommentId);
    }
}
