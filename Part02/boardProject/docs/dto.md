# Data Transfer Object (DTO)
도메인 코드와 DTO코드를 확실히 나누어서 서비스 코드에서 마무리된 데이터를 컨트롤러로 옮기게끔 함.

`Service Layer`: DTO, domain 모두 알고 있음
`Controller Layer`: DTO만 알고 있음 (DTO는 모르고 ResponseDTO, RequestDTO만 알 수도 있음)
## Response DTO
예시: `ArticleCommentResponse`

응답으로 내보내는 전용 DTO. 즉 컨트롤러에서만 사용. &rarr; DTO의 의존성이 ResponseDTO로 최소화될 예정.
## OSIV (Open Session In View)
트랜잭션 범위(&rarr;Service&rarr;Repository)를 넘어가는 레이어에서도 여전히 새션이 살아있음.

장) 재사용이 편하다. 개발이 편하다...  
단) 대량 처리에서는 올바르지 않다.

요새 시선: 상황에 따라서 선택적으로 쓰고 사용하지 않을땐 확실하게 닫아두자.