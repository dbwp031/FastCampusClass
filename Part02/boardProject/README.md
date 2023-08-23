# Board Project
가장 기본적이고 보편적인 게시판 기능을 둘러볼 수 있는 서비스입니다. 

## 개발 환경
* Intellij IDEA Ultimate 2023.1
* Java 17
* Gradle
* Spring boot 3.1.2

* ## 기술 세부 스택

Spring Boot

* Spring Boot Actuator
* Spring Web
* Spring Data JPA
* Rest Repositories
* Rest Repositories HAL Explorer
* Thymeleaf
* Spring Security
* H2 Database
* MySQL Driver
* Lombok
* Spring Boot DevTools
* Spring Configuration Processor

그 외

* QueryDSL
* Bootstrap

## 정리 내용
* [Mock, Mockito, BDDMockito 란](docs/Mock.md)
* [Page, Pageable 이란](docs/page.md)
* [Forward vs Redirect](docs/forwardVSredirect.md)
* [dto 란](docs/dto.md)
## 새롭게 알게 된 내용
* `@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = PaginationService.class)`
    * `webEnvironment와 classes`설정으로 SpringBootTest 무게 줄일 수 있음.
* `IntStream(startNumber, endNumber)`
  * 원시 타입 int 형태의 stream 생성 `IntStream(생략).boxed().toList()`로 List로 변경 가능 (`boxed` `int` &rarr; `Integer` )

* `@ParameterizedTest(name = "[{index}] {0}, {1} => {2}")`
    * 위 어노테이션을 사용해서 다양한 케이스에 대하여 테스트 할 수 있다.
    * `void testName(int p1, int p2, List<Integer> p3)` 이 함수에 대해 ParameterizedTest한다고 했을 때, `    static Stream<Arguments> testName()` 같은 함수명으로 재정의해서 테스트 케이스에 대해서 리턴해주면 된다.

* 다대다매핑 (ManyToMany) 사용하지 않는 이유
  * 우리가 직접 컨트롤 할 수 없기 때문.
  * DB 상에서는 다대다 매핑으로 인해서 각각을 FK로 가지는 Table이 생성된다. 하지만 우리가 다대다를 일대다, 다대일로 나누지 않으면 개발자들은 그것에 대해 컨트롤하지 못한다.
* 
## 새롭게 알게 된 내용 BackLog
* `@MockBean`
* `@MethodSource`
* `getReferenceById`

## 유용한 사이트
`.gitignore` 쉽게 생성해주는 사이트
  * https://www.toptal.com/developers/gitignore/
