package com.Estructura.API.repository;

import com.Estructura.API.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessagesRepository extends JpaRepository<Message,Long> {
    public List<Message> findMessagesBySenderIdAndRecipientId(int senderId,int recipientId);
    public List<Message> findMessagesBySenderIdOrRecipientId(int senderId,int recipientId);
}
