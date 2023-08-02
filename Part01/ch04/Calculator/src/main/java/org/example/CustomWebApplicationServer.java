package org.example;

import org.example.Calculator;
import org.example.calculator.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CustomWebApplicationServer {
    private final int port;
    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client.");

            // 서버 소켓이 클라이언를 기다리게 한다.
            // 소켓은 데이터를 내보내거나 그 세계로부터 데이터를 받기 위한 실제적인 창구 역할
            // 소켓은 프로토콜, IP주소, 포트 넘버로 정의된다.
            while ((clientSocket = serverSocket.accept()) != null) {
                logger.info("[CustomWebApplicationServer] client connected!");
                /*
                * Step 1 - 사용자 요청을 메인 Thread가 처리하도록 한다.
                 */
                try (InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                    DataOutputStream dos = new DataOutputStream(out);

//                    String line;
//                    while ((line = br.readLine()) != "") {
//                        System.out.println(line);
//                    }
//                    GET / HTTP/1.1
//                    Host: localhost:8080
//                    Connection: Keep-Alive
//                    User-Agent: Apache-HttpClient/4.5.14 (Java/17.0.6)
//                    Accept-Encoding: br,deflate,gzip,x-gzip
                    // -> http 프로토콜의 생김새
                    // 내부적으로 저런 프로토콜이 들어왔을 때 파싱을 해서 어떤 요청임을 판단해야 함
                    // 또한 판단에 맞게 Spring한테 전달해주어야 함
                    // 이 수행을 Tomcat이 해준다.
                    // 이 장에서 custom한 톰캣을 만드는 것이다.


                    /*
                    * 핵심 포인트: HTTP 프로토콜이 있기 때문에 우리가 규약에 맞춰  코드를 작성할 수 있었다.
                    * */
                    HttpRequest httpRequest = new HttpRequest(br);
                    if (httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")) {
                        QueryStrings queryStrings = httpRequest.getQueryStrings();

                        int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
                        String operator = queryStrings.getValue("operator");
                        int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));
                        int result = Calculator.calculate(new PositiveNumber(operand1), operator, new PositiveNumber(operand2));
                        byte[] body = String.valueOf(result).getBytes();

                        HttpResponse httpResponse = new HttpResponse(dos);
                        httpResponse.response200Header("applicatoin/json",body.length);
                        httpResponse.responseBody(body);
                    }
                }
            }
        }
    }
}
