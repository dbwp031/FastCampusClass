# Redirect VS Forward
Java Server Pages(JSP)에서 요청을 다룰 때 주로 사용되는 두 가지 방법
## Forward
* 현재의 요청을 다른 리소스(JSP, Servlet 등)에 전달하며, 그 리소스의 처리 결과를 클라이언트에게 응답으로 전송한다.
* URL: 브라우저의 URL은 변경되지 않는다.
* Request/Response: 원래의 `HttpServletRequest, HttpServletResponse` 객체가 그대로 전달된다.
## Redirect
* `Redirect`는 브라우저에게 다른 URL로의 새로운 요청을 생성하라는 지시를 보낸다. (HTTP Response 302 status code)
* URL: 새로운 리소스의 URL로 변경된다.
* Request/Response: 원래의 `HttpServletRequest, HttpServletResponse` 객체 사용하지 못한다.
