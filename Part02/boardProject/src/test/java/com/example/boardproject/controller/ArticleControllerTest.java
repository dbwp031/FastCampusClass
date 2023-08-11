package com.example.boardproject.controller;

import com.example.boardproject.config.SecurityConfig;
import com.example.boardproject.dto.ArticleDto;
import com.example.boardproject.dto.ArticleWithCommentsDto;
import com.example.boardproject.dto.UserAccountDto;
import com.example.boardproject.service.ArticleService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
    private final MockMvc mvc;
    @MockBean private ArticleService articleService;

    public ArticleControllerTest(@Autowired MockMvc mvc) { // test에서는 @Autowired 생략하면 안됨
        this.mvc = mvc;
    }
//    @Disabled("구현 중")
    @DisplayName("[view] [GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
        // Given
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());

        // any -> Matcher인데 필드 일부만 Mathcer일 수 없음.
        // 전부다 Matcher이어야 함. -> eq 사용해서 Matcher 사용

        // When & Then
        mvc.perform(get("/articles")) // cntrl + space + space -> suggestions & alt + enter -> import statically
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)) // contentType -> exact하게 맞을때만 통과 & contentTypeCompatibleWith -> 호환 가능한 애들까지 통과
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles")); // -> model attribute의 Map에 articles라는 키가 있는지 확인
        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
    }

    @DisplayName("[view] [GET] 게시글 상세 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleView_thenReturnsArticleView() throws Exception {
        // Given
        Long articleId = 1L;
        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());

        // When & Then
        mvc.perform(get("/articles/1")) // cntrl + space + space -> suggestions & alt + enter -> import statically
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments")); // -> model attribute의 Map에 articles라는 키가 있는지 확인
        then(articleService).should().getArticle(articleId);
    }
    @Disabled("구현 중")

    @DisplayName("[view] [GET] 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {
        // Given
        // When & Then
        mvc.perform(get("/articles/search")) // cntrl + space + space -> suggestions & alt + enter -> import statically
                .andExpect(status().isOk())
                .andExpect(view().name("articles/search"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)); // -> model attribute의 Map에 articles라는 키가 있는지 확인
    }
    @Disabled("구현 중")

    @DisplayName("[view] [GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnsArticleHashtagSearchView() throws Exception {
        // Given
        // When & Then
        mvc.perform(get("/articles/search-hashtag")) // cntrl + space + space -> suggestions & alt + enter -> import statically
                .andExpect(status().isOk())
                .andExpect(view().name("articles/search-hashtag"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)); // -> model attribute의 Map에 articles라는 키가 있는지 확인
    }
    private ArticleDto createArticleDto() {
        return ArticleDto.of(
                1L,
                createUserAccountDto(),
                "title",
                "content",
                "#java",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
                "#java",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "uno",
                "pw",
                "uno@mail.com",
                "Uno",
                "memo",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }
}