package com.openclassrooms.chatop.rentals;

import com.openclassrooms.chatop.dto.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Tag(name = "Rental", description = "Endpoints for rentals")
public class RentalController {

    private final ModelMapper modelMapper;
    private final RentalService rentalService;

    @Operation(summary = "To retrieve all the rentals")
    @GetMapping
    public ResponseEntity<RentalsListDTO> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();

        List<RentalDTO> rentalDtos = rentals.stream()
                .map(rental -> modelMapper.map(rental, RentalDTO.class))
                .collect(Collectors.toList());


        return ResponseEntity.ok(new RentalsListDTO(rentalDtos));
    }


    @Operation(summary = "To retrieve a rental information")
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Integer id) {
        return rentalService.getRentalById(id)
                .map(rental -> modelMapper.map(rental, RentalDTO.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "To create a rental")
    @PostMapping
    public ResponseEntity<MessageResponse> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("picture") MultipartFile image,
            @AuthenticationPrincipal Jwt jwt
    ) throws IOException {

        Integer ownerId = ((Long) jwt.getClaim("userId")).intValue();

        String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path uploadPath = Paths.get("uploads/");
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(filename);
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        rentalService.registerRental(name, surface, price, description, ownerId, filename);

        return ResponseEntity.ok(new MessageResponse("Rental created !"));
    }

    @Operation(summary = "To update a rental information")
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateRental(
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @PathVariable("id") Integer rentalId,
            @AuthenticationPrincipal Jwt jwt
    ) throws IOException {

        Integer connectedUserId = ((Long) jwt.getClaim("userId")).intValue();
        rentalService.updateRental(rentalId, name, surface, price, description, connectedUserId);

        return ResponseEntity.ok(new MessageResponse("Rental updated !"));
    }
}
