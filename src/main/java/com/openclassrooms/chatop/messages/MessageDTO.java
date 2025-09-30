package com.openclassrooms.chatop.messages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageDTO {
    @NotNull
    private Integer rental_id;
    @NotNull
    private Integer user_id;
    @NotBlank
    private String message;
}
