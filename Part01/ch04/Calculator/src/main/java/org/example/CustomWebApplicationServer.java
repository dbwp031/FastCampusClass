package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

            }
        }
    }
}
