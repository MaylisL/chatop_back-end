package com.openclassrooms.chatop.auth;

import com.openclassrooms.chatop.security.JwtService;
import com.openclassrooms.chatop.users.UserService;
import com.openclassrooms.chatop.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("api/auth")
@RequiredArgsConstructor()
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody LoginRequest request) {
        String token = getTokenFromAuthentication(request.email(), request.password());

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody RegisterRequest request) {
        User savedUser = userService.register(
                request.email(),
                request.name(),
                request.password()
        );

        String token = getTokenFromAuthentication(request.email(), request.password());
        return ResponseEntity.ok(Map.of("token", token));
    }

    private String getTokenFromAuthentication(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        return jwtService.generateToken(authentication);
    }
}
