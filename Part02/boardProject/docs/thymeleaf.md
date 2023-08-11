# Thymeleaf

### 예시 1
```xml
<attr sel="main" th:object="${articles}">
    <attr sel="#search-type" th:remove="all-but-first">
        <attr sel="option[0]" th:each="searchType : ${searchTypes}"
        th:value="${searchType.name}"
        th:text="${searchType.description}"
        th:selected="${param.searchType != null && (param.searchType.toString == searchType.name)}"
        />
    </attr>
</attr>
```
* `th:remove="all=but-first`
  * all-but-first: Remove all children of the containing tag except the first one.
  * https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html#expression-basic-objects
* `param`
  * `${x}` will return a variable `x` stored into the Thymeleaf context or as an exchange attribute (A “request attribute” in Servlet jargon). 
  * `${param.x}` will return a `request parameter` called `x` (which might be multivalued). 
  * `${session.x}` will return a `session` attribute called `x`. 
  * `${application.x}` will return an `application attribute` called `x` (a “servlet context attribute” in Servlet jargon).