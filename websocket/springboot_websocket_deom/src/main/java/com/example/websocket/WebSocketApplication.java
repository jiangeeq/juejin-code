package com.example.websocket;

import com.example.websocket.socket.BulletinWebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebSocketApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(WebSocketApplication.class, args);
        // 注入applicationContext
        BulletinWebSocket.setApplicationContext(applicationContext);
    }
}