package org.example;

import org.example.Calculator;
import org.example.calculator.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomWebApplicationServer {
    private final int port;
    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {

        /*
        * Step 2
        * */
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client.");

            // 서버 소켓이 클라이언를 기다리게 한다.
            // 소켓은 데이터를 내보내거나 그 세계로부터 데이터를 받기 위한 실제적인 창구 역할
            // 소켓은 프로토콜, IP주소, 포트 넘버로 정의된다.
            while ((clientSocket = serverSocket.accept()) != null) {
                logger.info("[CustomWebApplicationServer] client connected!");

                // step 3 -> ThreadPool에서 실행한다.
                executorService.execute(new ClientRequestHandler(clientSocket));
                // 발생할 수 있는 이슈
                // Thread -> 생성될 때마다 독립적인 STACK 메모리를 할당 받음
                // 메모리 할당 -> 비싼 작업
                // 스레드를 미리 생성해 둔 후 요청이 들어올 때마다 거기서 뽑아쓰는 방법: Thread Pool

            }
        }
    }
}
