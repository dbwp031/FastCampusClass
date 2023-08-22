package com.example.boardproject.controller;

import com.example.boardproject.dto.UserAccountDto;
import com.example.boardproject.dto.request.ArticleCommentRequest;
import com.example.boardproject.dto.security.BoardPrincipal;
import com.example.boardproject.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/*
* @AuthenticationPrincipal: 어노테이션은 현재 인증된 사용자의 정보에 쉽게 접근할 수 있도록 도화주는 역할은 한다.
* */
@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {
    private final ArticleCommentService articleCommentsService;
    @PostMapping("/new")
    public String postNewArticleComment(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            ArticleCommentRequest articleCommentRequest
    ) {
        articleCommentsService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()));
        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticle(
            @PathVariable Long commentId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            Long articleId) {
        articleCommentsService.deleteArticleComment(commentId, boardPrincipal.getUsername());

        return "redirect:/articles/" + articleId;
    }
}
