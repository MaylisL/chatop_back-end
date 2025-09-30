package com.openclassrooms.chatop.rentals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalsListDTO {

    private List<RentalDTO> rentals;

}

