package com.example.boardproject.service;

import com.example.boardproject.domain.Article;
import com.example.boardproject.domain.Hashtag;
import com.example.boardproject.domain.UserAccount;
import com.example.boardproject.domain.constant.SearchType;
import com.example.boardproject.dto.ArticleDto;
import com.example.boardproject.dto.ArticleWithCommentsDto;
import com.example.boardproject.repository.ArticleRepository;
import com.example.boardproject.repository.HashtagRepository;
import com.example.boardproject.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
* @Transactional
* 클래스 레벨에서 @Transactional을 걸어주면, 클래스 내의 전체 매소드를 하나의 트랜잭션 취급한다.
* 이 뜻은, 클래스 내부의 여러 매소드에 걸쳐 하나의 트랙잭션으로 사용한다는 의미로, 하나의 매소드에서 에러가 발생하면 다른 매소드에 영향을 미칠 수 있다.
* 반대로, 매소드 레벨에서 @Transactional을 걸어주면 메소드끼리 영향을 받지 않는다.
* */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
    private final HashtagService hashtagService;
    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;
    private final HashtagRepository hashtagRepository;
    /*
    * Page 클래스
    * 데이터의 페이지네이션을 처리하기 위한 클래스
    * 주로 데이터를 페이지 단위로 나누어 표시하거나 조회할 때 사용
    * */
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }
        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME ->articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtagNames(Arrays.stream(searchKeyword.split(" ")).toList(),
                    pageable)
                    .map(ArticleDto::from);
        };
    }
    @Transactional(readOnly = true)
    public ArticleDto searchArticle(long l) {
        return null;
    }
    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }
    public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
        Set<Hashtag> hashtags = renewHashtagsFromContent(dto.content());

        Article article = dto.toEntity(userAccount);
        article.addHashtags(hashtags);
        articleRepository.save(article);
    }

    public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());

            if (article.getUserAccount().equals(userAccount)) {
                if(dto.title()!=null){
                    article.setTitle(dto.title());
                }
                if (dto.content() != null) {
                    article.setContent(dto.content());
                }
                Set<Long> hashtagIds = article.getHashtags().stream()
                        .map(Hashtag::getId)
                        .collect(Collectors.toUnmodifiableSet());
                article.clearHashtags();
                articleRepository.flush();

                hashtagIds.forEach(hashtagService::deleteHashtagWithoutArticles);

                Set<Hashtag> hashtags = renewHashtagsFromContent(dto.content());
                article.addHashtags(hashtags);
            }

        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 수정하는 데 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    public void deleteArticle(Long articleId, String userId) {
        Article article = articleRepository.getReferenceById(articleId);
        Set<Long> hashtagIds = article.getHashtags().stream()
                        .map(Hashtag::getId)
                        .collect(Collectors.toUnmodifiableSet());

        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
        articleRepository.flush();

        hashtagIds.forEach(hashtagService::deleteHashtagWithoutArticles);
    }
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtagName, Pageable pageable) {
        if (hashtagName == null || hashtagName.isBlank()) {
            return Page.empty(pageable);
        }
        return articleRepository.findByHashtagNames(List.of(hashtagName), pageable).map(ArticleDto::from);
    }

    public List<String> getHashtags() {
        return hashtagRepository.findAllHashtagNames(); //TODO: HashtagService로 구현이 더 좋을수도.
    }


    public long getArticleCount() {
        return articleRepository.count();
    }

    private Set<Hashtag> renewHashtagsFromContent(String content) {
        Set<String> hashtagNamesInContent = hashtagService.parseHashtagNames(content);
        Set<Hashtag> hashtags = hashtagService.findHashtagByNames(hashtagNamesInContent);
        Set<String> existingHashtagNames = hashtags.stream()
                .map(Hashtag::getHashtagName)
                .collect(Collectors.toUnmodifiableSet());

        hashtagNamesInContent.forEach(newHashtagName -> {
            if (!existingHashtagNames.contains(newHashtagName)) {
                hashtags.add(Hashtag.of(newHashtagName));
            }
        });

        return hashtags;

    }
}
