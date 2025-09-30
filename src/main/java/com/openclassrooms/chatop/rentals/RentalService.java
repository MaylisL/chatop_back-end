package com.openclassrooms.chatop.rentals;


import com.openclassrooms.chatop.exception.ResourceNotFoundException;
import com.openclassrooms.chatop.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {
    @Value("${SERVER_URL}")
    private String serverUrl;

    private final RentalRepository rentalRepository;

    public void registerRental(String name, Double surface, Double price, String description, Integer ownerId, String filename) {
        Rental rental = new Rental();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        User owner = new User();
        owner.setId(ownerId);
        rental.setOwner(owner);
        rental.setPicture(serverUrl + "/uploads/" + filename);

        rentalRepository.save(rental);
    }

    public void updateRental(Integer rentalId, String name, Double surface, Double price, String description) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id " + rentalId));

        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);

        rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Integer id) {
        return rentalRepository.findById(id);
    }

}
