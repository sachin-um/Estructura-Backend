package com.Estructura.API.service;

import com.Estructura.API.model.Message;
import com.Estructura.API.requests.chat.SendMessageRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MessagesService {
    public GenericAddOrUpdateResponse<SendMessageRequest> sendMessage(
        @ModelAttribute SendMessageRequest request,
        String token,
        int recipientId
    );

    public List<Message> getMessages(String token,int recipientId);
}
