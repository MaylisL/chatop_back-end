package com.openclassrooms.chatop.rentals;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentalDTO {
    private Integer id;
    private String name;
    private Double surface;
    private Double price;
    private String picture;
    private String description;
    private Integer owner_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
