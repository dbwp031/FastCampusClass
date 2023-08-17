package com.example.boardproject.controller;

import com.example.boardproject.dto.UserAccountDto;
import com.example.boardproject.dto.request.ArticleCommentRequest;
import com.example.boardproject.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {
    private final ArticleCommentService articleCommentsService;
    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {
        // TODO: 인증정보를 넣어줘야 한다.
        articleCommentsService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
                "uno","pw","uno@mail.com","Uno","memo"
        )));
        return "redirect:/articles/"+articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticle(@PathVariable Long commentId, Long articleId) {
        articleCommentsService.deleteArticleComment(commentId);

        return "redirect:/articles/" + articleId;
    }
}
