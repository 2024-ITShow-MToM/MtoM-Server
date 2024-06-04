package com.MtoM.MtoM.domain.chat;

import com.MtoM.MtoM.domain.chat.domain.ChatMessage;
import com.MtoM.MtoM.domain.chat.service.ChatMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatMessageService chatMessageService;

    public ChatController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping("/messages")
    public List<ChatMessage> getMessages(@RequestParam String senderId, @RequestParam String receiverId) {
        return chatMessageService.getMessages(senderId, receiverId);
    }

    @GetMapping("/chat")
    public String getChatPage() {
        return "chat";
    }

    @GetMapping("/chat/notifications")
    public Map<String, Object> getNotifications(@RequestParam String userId) {
        List<ChatMessage> messages = chatMessageService.getMessagesForUser(userId);
        ChatMessage lastMessage = chatMessageService.getLastMessageForUser(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("messageCount", messages.size());
        response.put("lastMessage", lastMessage != null ? lastMessage.getMessage() : null);
        response.put("lastMessageTime", lastMessage != null ? lastMessage.getTimestamp() : null);
        response.put("lastSenderId", lastMessage != null ? lastMessage.getSender().getId() : null);

        return response;
    }
}