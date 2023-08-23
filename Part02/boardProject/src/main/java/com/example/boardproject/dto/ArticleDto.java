package com.example.boardproject.dto;

import com.example.boardproject.domain.Article;
import com.example.boardproject.domain.UserAccount;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/*레코드(record)란 "데이터 클래스"이며 순수하게 데이터를 보유하기 위한 특수한 종류의 클래스이다.
코틀린의 데이터 클래스와 비슷한 느낌이라고 보면 된다. 밑에서 코드를 보겠지만,
record 클래스를 정의할때, 그 모양은 정말 데이터의 유형만 딱 나타내는 듯한 느낌이다.
훨씬더 간결하고 가볍기 때문에 Entity 혹은 DTO 클래스를 생성할때 사용되면 굉장히 좋을 듯하다.*/
public record ArticleDto(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,

        Set<HashtagDto> hashtagDtos,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
){

    public static ArticleDto of(UserAccountDto userAccountDto, String title, String content, Set<HashtagDto> hashtagDtos) {
        return new ArticleDto(null, userAccountDto, title, content, hashtagDtos, null, null, null, null);
    }
    /*
    * Effective Java
    * 아이템 1: 생성자 대신 정적 팩터리 메서드를 고려하라
    * of: 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메서드
    * */
    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, Set<HashtagDto> hashtagDtos, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtagDtos, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtags().stream()
                        .map(HashtagDto::from)
                        .collect(Collectors.toUnmodifiableSet()),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public Article toEntity(UserAccount userAccount) {
        return Article.of(
                userAccount,
                title,
                content
        );
    }

}
