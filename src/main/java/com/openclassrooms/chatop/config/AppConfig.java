package com.openclassrooms.chatop.config;

import com.openclassrooms.chatop.rentals.Rental;
import com.openclassrooms.chatop.rentals.RentalDTO;
import com.openclassrooms.chatop.users.User;
import com.openclassrooms.chatop.users.UserDTO;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Rental.class, RentalDTO.class)
                .addMapping(src -> src.getOwner().getId(), RentalDTO::setOwner_id)
                .addMapping(Rental::getCreatedAt, RentalDTO::setCreated_at)
                .addMapping(Rental::getUpdatedAt, RentalDTO::setUpdated_at);

        modelMapper.typeMap(User.class, UserDTO.class)
                .addMapping(User::getCreatedAt, UserDTO::setCreated_at)
                .addMapping(User::getUpdatedAt, UserDTO::setUpdated_at);

        return modelMapper;
    }

    // Swagger configuration
    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme bearerAuthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        return new OpenAPI()
                .info(new Info()
                        .title("ChatOp Rental API")
                        .version("1.0")
                        .description("API for rental application with JWT authentication"))
                .schemaRequirement("bearerAuth", bearerAuthScheme).addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
