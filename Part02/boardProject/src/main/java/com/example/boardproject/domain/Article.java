package com.example.boardproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"), // 상속하는 필드
        @Index(columnList = "createdBy"), // 상속하는 필드
})
@Entity
public class Article extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false) private String title; //제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    @Setter private String hashtag; // 해시태그

    // 실무에서는 양방향 매핑을 안할 때가 있음 -> cascade로 강하게 결합되기 때문에 data migration이나 편집할 때 불편함.
    // 원치않는 데이터 손실이 일어날 수 있음.
    // 운영의 입장에서는 일부로 FK 안걸고 사용할 수도 있음. (ex. 게시글 삭제되어도 댓글은 남기고 싶을 때)
    // mappedBy 사용하지 않으면 article_articleComment 생성됨
    //- CascadeType.ALL: 모든 Cascade 를 적용한다.
    // - CascadeType.PERSIST: 엔티티를 영속화할 때, 연관된 엔티티도 함께 영속화한
    // - CascadeType.REMOVE: 엔티티를 제거할 때, 연관된 엔티티도 모두 제거한다.
    // - CascadeType.MERGE: 엔티티를 병합할 때, 연관된 엔티티도 모두 병합한다.
    // - CascadeType.REFRESH: 엔티티를 새로고침할 때, 연관된 엔티티도 모두 새로고침한다.
    // - CascadeType.DETACH: 엔티티를 detach 할 때, 연관된 엔티티도 모두 detach 가 되어서 변경 사항이 반영되지 않는다.
    @ToString.Exclude
    // ToString은 각 필드를 찍어서 보여준다.
    // aritcleComments -> 각 ArticleComment -> article -> articleComments -> ... 순환참조가 발생해버린다.
    // ToString을 끊어주여야 한다. 보통 이쪽에서 끊어준다.
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    protected Article() {}

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
