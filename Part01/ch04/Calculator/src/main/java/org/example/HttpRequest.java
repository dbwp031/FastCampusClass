package org.example;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private final RequestLine requestLine;
    public HttpRequest(BufferedReader br) throws IOException {
        this.requestLine = new RequestLine(br.readLine());
    }

    public boolean isGetRequest() {
        // HttpRequest.isGetRequest -> requestLine.isGetRequest -> 처리
        // 이렇게 이 reqeust가 GET request인지 확인하는 것의 책임은
        // HttpRequest가 가지고 있는 것이 아니라 requestLine이 가지고 있다.
        // 따라서 requestLine.isGetRequest()로 한 번 더 들어가준다.
        return requestLine.isGetRequest();
    }

    public boolean matchPath(String urlPath) {
        return requestLine.matchPath(urlPath);
    }

    public QueryStrings getQueryString() {
        return requestLine.getQueryStrings();
    }

    public QueryStrings getQueryStrings() {
        return requestLine.getQueryStrings();
    }
}
