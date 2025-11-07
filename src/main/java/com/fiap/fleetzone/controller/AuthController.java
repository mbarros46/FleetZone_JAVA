package com.fiap.fleetzone.controller;

// ...existing code...
import com.fiap.fleetzone.dto.AuthDTOs.AuthLoginRequest;
import com.fiap.fleetzone.dto.AuthDTOs.AuthRegisterRequest;
import com.fiap.fleetzone.dto.AuthDTOs.AuthResponse;
import com.fiap.fleetzone.service.AuthService;
import com.fiap.fleetzone.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService auth;
    private final UserService userService;

    public AuthController(AuthService auth, UserService userService) {
        this.auth = auth;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRegisterRequest body) {
        try {
            AuthResponse resp = auth.register(body);
            return ResponseEntity.status(201).body(resp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest body) {
        try {
            AuthResponse resp = auth.login(body);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            var u = userService.getUsuarioFromAuthHeader(authHeader);
            return ResponseEntity.ok(u);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}