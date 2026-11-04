package com.spring.render.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.render.dto.MessageDto;
import com.spring.render.models.MessageModel;
import com.spring.render.services.MessageService;

@RestController
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/allmessages")
    public ResponseEntity<List<MessageModel>> getAllMessages() {
        // Banc d'essai SmartDeploy : panne simulée, l'API répond en erreur 500
        // 60 secondes après le démarrage, alors que les pods restent sains.
        if (java.lang.management.ManagementFactory.getRuntimeMXBean().getUptime() > 60_000) {
            throw new IllegalStateException("Panne simulée : API défaillante (banc d'essai SmartDeploy)");
        }
        var messageList = messageService.allMessages();

        return ResponseEntity.ok().body(messageList);
    }

    @PostMapping("/sendmessage")
    public ResponseEntity<MessageModel> postMessage(@RequestBody MessageDto messageDto) {
        var newMessage = messageService.createMessage(messageDto.message());

        return ResponseEntity.status(HttpStatus.CREATED).body(newMessage);
    }

    @DeleteMapping("/deletemessage/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable("id") String id) {

        messageService.deleteMessage(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
