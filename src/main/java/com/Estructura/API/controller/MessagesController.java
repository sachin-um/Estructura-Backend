package com.Estructura.API.controller;

import com.Estructura.API.model.Message;
import com.Estructura.API.requests.chat.SendMessageRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.service.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessagesController {
    private final MessagesService messagesService;

    @PostMapping
    public GenericAddOrUpdateResponse<SendMessageRequest> sendMessage(
        @ModelAttribute SendMessageRequest request,
        @RequestParam("token") String token,
        @RequestParam("to") int recipientId
    ) {
        return messagesService.sendMessage(request,token,recipientId);
    }

    @GetMapping
    public List<Message> getMessages(
        @RequestParam("token") String token,
        @RequestParam("to") int recipientId
    ) {
        return messagesService.getMessages(token,recipientId);
    }
}
