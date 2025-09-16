package com.openclassrooms.chatop.auth;

public record RegisterRequest(
        String email,
        String name,
        String password ) {
}
