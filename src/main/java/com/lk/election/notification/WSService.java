package com.lk.election.notification;

import com.lk.election.models.responseModels.MessageBrokerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyFrontend(final MessageBrokerResponse response) {
        //Sending Notification
        messagingTemplate.convertAndSend("/topic/messages", response);
    }

    public void notifyUser(final String id, final MessageBrokerResponse response) {

        messagingTemplate.convertAndSendToUser(id, "/topic/private-messages", response);
    }
}