package com.Estructura.API.service;

import com.Estructura.API.model.Message;
import com.Estructura.API.requests.chat.SendMessageRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public interface MessagesService {
    GenericAddOrUpdateResponse<SendMessageRequest> sendMessage(
        @ModelAttribute SendMessageRequest request,
        String token,
        int recipientId
    );

    List<Message> getMessages(String token,int recipientId);

    List<Message> getAllMessages(String token);

    void seen(Long id, String token);
}
