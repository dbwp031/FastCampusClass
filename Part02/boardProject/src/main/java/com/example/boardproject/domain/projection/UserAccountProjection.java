package com.example.boardproject.domain.projection;

import com.example.boardproject.domain.UserAccount;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
/*
* 스프링 프레임워크에서 "Projection"은 디비에서 검색된 엔티티의 일부 속성만 선택적으로 조회하는 방법을 나타낸다.
* 이는 데이터베이스 쿼리의 성능을 최적화하고 필요한 데이터만을 가져오는 데 도움이 된다.
* 1) 인터페이스 기반 프로젝션: 인터페이스를 정의하고 해당 인터페이스를 사용하여 필요한 엔티티 속성을 선택적으로 조회하는 방식 (사용한 방식)
* 2) 클래스 기반 프로젝션: 특정 클래스를 정의하여 엔티티의 일부 속성을 포함하는 생성자 또는 메서드를 사용하여 조회하는 방식
* */
@Projection(name="withoutPassword", types = UserAccount.class)
public interface UserAccountProjection {
    String getUserId();
    String getEmail();
    String getNickname();
    String getMemo();
    LocalDateTime getCreatedAt();
    String getCreatedBy();
    LocalDateTime getModifiedAt();
    String getModifiedBy();

}
