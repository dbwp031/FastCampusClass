package com.example.boardproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/*
* @Index를 사용하면 데이터베이스 테이블의 특정 컬럼에 대한 데이터의 검색 속도를 향상시킬 수 있습니다.
* 하지만 추가적인 저장공간을 사용하니깐, 잘 정해서 사용해야 합니다.
* */
@Getter
@ToString(callSuper = true)
@Table(indexes={
        @Index(columnList = "hashtagName", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Hashtag extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToMany(mappedBy = "hashtags")
    private Set<Article> articles = new LinkedHashSet<>();

    @Setter @Column(nullable = false) private String hashtagName;

    protected Hashtag() {}

    private Hashtag(String hashtagName) {
        this.hashtagName = hashtagName;
    }

    public static Hashtag of(String hashtagName) {
        return new Hashtag(hashtagName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hashtag hashtag = (Hashtag) o;
        return this.getId()!=null && Objects.equals(id, hashtag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
