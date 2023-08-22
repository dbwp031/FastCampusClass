package com.example.boardproject.dto.response;

import com.example.boardproject.dto.ArticleCommentDto;

import java.io.Serializable;
import java.time.LocalDateTime;
// Serializable ->
// 스레드 안전성: 직렬화된 객체는 불변성을 유지한다.
// 객체의 영속성: 직렬화를 통해 객체의 상태를 바이트 스트림 형태로 저장 가능하다. ...
public record ArticleCommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname,
        String userId
) implements Serializable {
    public static ArticleCommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId) {
        return new ArticleCommentResponse(id, content, createdAt, email, nickname, userId);
    }
    public static ArticleCommentResponse from(ArticleCommentDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new ArticleCommentResponse(
                dto.id(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname,
                dto.userAccountDto().userId()
        );
    }
}
