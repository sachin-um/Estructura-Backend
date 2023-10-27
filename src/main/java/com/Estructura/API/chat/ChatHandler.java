package com.Estructura.API.chat;

import com.Estructura.API.config.JwtService;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

// TODO: A lot left to do
@RequiredArgsConstructor
public class ChatHandler implements WebSocketHandler {
    private final Map<String, WebSocketSession> userSessions = new HashMap<>();
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session)
        throws Exception {
        String token = Objects.requireNonNull(session.getUri())
            .getQuery().substring(6);
        System.out.println("Token : " + token);
        try {
            String username = jwtService.extractUsername(token);
            if (username != null) {
                System.out.println(username);
                UserDetails user = userDetailsService
                    .loadUserByUsername(username);
                if (user != null) {
                    System.out.println("WebSocket connection established: " +
                        session.getId() + ":" + user.getUsername());
                    userSessions.put(user.getUsername(),session);
                    return;
                }
            }
        } catch (NullPointerException | MalformedJwtException e) {
            System.out.println("Invalid Token");
        } catch (UsernameNotFoundException e) {
            System.err.println("No User found");
        }
        session.close();
    }

    @Override
    public void handleMessage(
        @NonNull WebSocketSession session,
        @NonNull WebSocketMessage<?> message
    ) {
        if (message instanceof TextMessage) {
            System.out.println(message.getPayload());
            try {
                session.sendMessage(
                    new TextMessage(message.getPayload().toString())
                );
            } catch (IOException e) {
                System.err.println("Error sending message");
            }
        }
    }

    @Override
    public void handleTransportError(
        @NonNull WebSocketSession session, @NonNull Throwable exception
    ) {
        System.err.println("Transport error for session " + session.getId());
    }

    @Override
    public void afterConnectionClosed(
        @NonNull WebSocketSession session, @NonNull CloseStatus closeStatus
    ) {
        System.out.println("WebSocket connection closed: " + session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}