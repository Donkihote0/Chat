package messenger.messenger.controller;

import messenger.messenger.model.ChatMessageEntity;
import messenger.messenger.repository.ChatMessageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin // Cho phép frontend gọi API
public class MessageHistoryController {

    private final ChatMessageRepository repository;

    public MessageHistoryController(ChatMessageRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{roomId}")
    public List<ChatMessageEntity> getMessages(@PathVariable String roomId) {
        return repository.findByRoomIdOrderByTimestampAsc(roomId);
    }
}

