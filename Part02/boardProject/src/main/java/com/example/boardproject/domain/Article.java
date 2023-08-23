package com.example.boardproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true) // 생성자까지 찎겠다.
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "createdAt"), // 상속하는 필드
        @Index(columnList = "createdBy"), // 상속하는 필드
})
@Entity
public class Article extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @JoinColumn(name="userId") @ManyToOne(optional = false) private UserAccount userAccount;
    //name-> 내(Article) 테이블의 FK 필드명
    //referencedColumnName -> 조일할 컬럼명
    // 결국 여기서는 article table과 user_account table과 연관관계인데, article table이 user_account의 user_id(referencedByName)필드를 user_id(name)이란 이름으로 저장한다.

    @Setter @Column(nullable = false) private String title; //제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    // persist -> save / merge -> update
    @ToString.Exclude
    @JoinTable(
            name = "article_hashtag",
            joinColumns = @JoinColumn(name = "articleId"),
            inverseJoinColumns = @JoinColumn(name = "hashtagId")
    )
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Hashtag> hashtags = new LinkedHashSet<>();

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
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    protected Article() {}

    private Article(UserAccount userAccount, String title, String content) {
        this.title = title;
        this.content = content;
        this.userAccount = userAccount;
    }

    public static Article of(UserAccount userAccount, String title, String content) {
        return new Article(userAccount, title, content);
    }

    public void addHashtag(Hashtag hashtag) {
        this.getHashtags().add(hashtag);
    }

    public void addHashtags(Collection<Hashtag> hashtags) {
        this.getHashtags().addAll(hashtags);
    }

    public void clearHashtags() {
        this.getHashtags().clear();
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
