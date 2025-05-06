package messenger.messenger.controller;

import messenger.messenger.model.ChatMessage;
import messenger.messenger.model.ChatMessageEntity;
import messenger.messenger.repository.ChatMessageRepository;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository repository;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatMessageRepository repository) {
        this.messagingTemplate = messagingTemplate;
        this.repository = repository;
    }

    @MessageMapping("/chat.send/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, @Payload ChatMessage message) {
        // Gửi thời gian gửi
        LocalDateTime now = LocalDateTime.now();
        message.setTime(now.format(DateTimeFormatter.ofPattern("HH:mm")));

        // Lưu vào DB
        ChatMessageEntity entity = new ChatMessageEntity();
        entity.setSender(message.getSender());
        entity.setContent(message.getContent());
        entity.setRoomId(roomId);
        entity.setTimestamp(now);
        repository.save(entity);

        // Gửi lại cho người dùng trong phòng
        messagingTemplate.convertAndSend("/topic/room/" + roomId, message);
    }
}



