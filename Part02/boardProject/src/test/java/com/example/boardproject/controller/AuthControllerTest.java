package com.example.boardproject.controller;

import com.example.boardproject.config.SecurityConfig;
import com.example.boardproject.config.TestSecurityConfig;
import com.example.boardproject.service.ArticleService;
import com.example.boardproject.service.PaginationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 인증")
@Import(TestSecurityConfig.class)
@WebMvcTest(Void.class)
public class AuthControllerTest {

    private final MockMvc mvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private PaginationService paginationService;

    public AuthControllerTest(@Autowired MockMvc mvc) { // test에서는 @Autowired 생략하면 안됨
        this.mvc = mvc;
    }
//    @Disabled("구현 중")
    @DisplayName("[view] [GET] 로그인 페이지 - 정상 호출")
    @Test
    void givenNothing_whenTryingToLogin_thenReturnsLoginView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/login")) // cntrl + space + space -> suggestions & alt + enter -> import statically
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
        then(articleService).shouldHaveNoInteractions();
        then(paginationService).shouldHaveNoInteractions();
    }

}
