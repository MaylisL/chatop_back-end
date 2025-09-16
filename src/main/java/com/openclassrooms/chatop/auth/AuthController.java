package com.openclassrooms.chatop.auth;

import com.openclassrooms.chatop.security.JwtService;
import com.openclassrooms.chatop.users.UserService;
import com.openclassrooms.chatop.users.User;
import com.openclassrooms.chatop.users.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("api/auth")
@RequiredArgsConstructor()
@Tag(name = "Authentication", description = "Endpoints for user login and register")
@Validated
public class AuthController {

    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Operation(summary = "Login user and return JWT", security = {})
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest request) {
        String token = getTokenFromAuthentication(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(new TokenDTO(token));
    }

    @Operation(summary = "Register new user", security = {})
    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@Valid @RequestBody RegisterRequest request) {
        User savedUser = userService.register(
                request.getEmail(),
                request.getName(),
                request.getPassword()
        );

        String token = getTokenFromAuthentication(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new TokenDTO(token));
    }

    @Operation(summary = "To retrieve connected user details")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(Authentication authentication) {
        String userEmail = authentication.getName();

        return userService.getUserByEmail(userEmail)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    private String getTokenFromAuthentication(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        return jwtService.generateToken(authentication);
    }

}
