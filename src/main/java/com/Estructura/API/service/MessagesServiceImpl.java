package com.Estructura.API.service;

import com.Estructura.API.config.JwtService;
import com.Estructura.API.model.Message;
import com.Estructura.API.model.User;
import com.Estructura.API.repository.MessagesRepository;
import com.Estructura.API.requests.chat.SendMessageRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessagesServiceImpl implements MessagesService {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final MessagesRepository messagesRepository;

    @Override
    public GenericAddOrUpdateResponse<SendMessageRequest> sendMessage(
        @ModelAttribute SendMessageRequest request, String token, int recipientId
    ) {
        GenericAddOrUpdateResponse<SendMessageRequest> response =
            new GenericAddOrUpdateResponse<>();
        if (response.checkValidity(request)) {
            try {
                String username = jwtService.extractUsername(token);
                UserDetails user = userDetailsService.loadUserByUsername(username);

                if (user != null) {
                    Optional<User> theUser = userService.findByEmail(user.getUsername());
                    Optional<User> recipient = userService.findById(recipientId);
                    if (recipient.isEmpty() || theUser.isEmpty()) {
                        response.addError("general", "Something not found");
                    } else {
                        Message message = Message.builder()
                            .content(request.getContent())
                            .recipientId(recipient.get().getId())
                            .senderId(theUser.get().getId())
                            .build();
                        Message savedMessage = messagesRepository.save(message);

                        List<String> savedFiles = new ArrayList<>();
                        List<String> savedFileOriginalNames = new ArrayList<>();

                        if (request.getFiles() != null) {
                            for (MultipartFile file : request.getFiles()) {
                                // .../senderId/recipientId
                                String originalName = file.getOriginalFilename();
                                String uploadDir =
                                    "./files/message-files/" + theUser.get().getId() + "/" +
                                        recipient.get().getId();

                                if (originalName != null) {
                                    String generatedName = FileUploadUtil.generateFileName(
                                        StringUtils.cleanPath(originalName));
                                    FileUploadUtil.saveFile(uploadDir, file, generatedName);
                                    savedFiles.add(generatedName);
                                    savedFileOriginalNames.add(originalName);
                                }
                            }
                        }

                        savedMessage.setFiles(savedFiles);
                        savedMessage.setFileOriginalNames(savedFileOriginalNames);
                        messagesRepository.save(savedMessage);
                        response.setSuccess(true);
                        response.setId(savedMessage.getId());
                    }
                } else {
                    response.addError("token", "token didn't correspond to a user");
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return response;
    }

    @Override
    public List<Message> getMessages(String token, int recipientId) {
        try {
            String username = jwtService.extractUsername(token);
            UserDetails user = userDetailsService.loadUserByUsername(username);

            if (user != null) {
                Optional<User> theUser = userService.findByEmail(user.getUsername());
                Optional<User> recipient = userService.findById(recipientId);
                if (recipient.isPresent() && theUser.isPresent()) {
                    List<Message> messages = messagesRepository
                        .findMessagesBySenderIdAndRecipientId(
                            theUser.get().getId(), recipient.get().getId()
                        );
                    // Other way around
                    messages.addAll(messagesRepository
                        .findMessagesBySenderIdAndRecipientId(
                            recipient.get().getId(), theUser.get().getId()
                        )
                    );
                    return messages;
                }
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return new ArrayList<>();
    }
}
