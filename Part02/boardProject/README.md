# Board Project
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

## 새롭게 알게 된 내용 BackLog
* `@MockBean`
* `@MethodSource`

## 유용한 사이트
`.gitignore` 쉽게 생성해주는 사이트
  * https://www.toptal.com/developers/gitignore/