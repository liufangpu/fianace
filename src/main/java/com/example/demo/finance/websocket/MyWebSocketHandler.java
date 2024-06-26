package com.example.demo.finance.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(MyWebSocketHandler.class);
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        log.info("Session added: {}", session.getId());
        try {
            session.sendMessage(new TextMessage("Connection established"));
        } catch (IOException e) {
            log.error("Error when sending welcome message: ", e);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("Received message: {}", message.getPayload());
        // Echo the message back to all connected sessions
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                try {
                    s.sendMessage(new TextMessage("Echo: " + message.getPayload()));
                } catch (IOException e) {
                    log.error("Error when echoing message back: ", e);
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        log.info("Session removed: {}", session.getId());
    }

    public void broadcast(String message) {
        log.info("Broadcasting message: {}", message);
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    log.error("Failed to broadcast message to session: {}", session.getId(), e);
                    sessions.remove(session);  // Remove session if we cannot communicate with it
                }
            }
        }
    }
}
