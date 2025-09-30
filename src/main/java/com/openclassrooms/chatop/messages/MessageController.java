package com.openclassrooms.chatop.messages;

import com.openclassrooms.chatop.dto.MessageResponse;
import com.openclassrooms.chatop.rentals.Rental;
import com.openclassrooms.chatop.users.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "Message", description = "Endpoints for messages")
public class MessageController {
    private final MessageService messageService;

    @Operation(summary = "create message")
    @PostMapping
    public ResponseEntity<MessageResponse> createMessage(
            @Valid @RequestBody MessageDTO messageDTO) {

        Message message = messageDtoToMessage(messageDTO);
        messageService.saveMessage(message);

        return ResponseEntity.ok(new MessageResponse("Message send with success"));
    }
    private Message messageDtoToMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setMessage(messageDTO.getMessage());

        User user = new User();
        user.setId(messageDTO.getUser_id());
        message.setUser(user);

        Rental rental = new Rental();
        rental.setId(messageDTO.getRental_id());
        message.setRental(rental);
        return message;
    }
}
