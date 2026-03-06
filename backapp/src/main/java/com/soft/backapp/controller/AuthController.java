package com.soft.backapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soft.backapp.dto.AuthRequest;
import com.soft.backapp.dto.AuthResponse;
import com.soft.backapp.dto.RegistrationRequest;
import com.soft.backapp.model.MyUser;
import com.soft.backapp.model.MyUserBuilder;
import com.soft.backapp.security.JwtService;
import com.soft.backapp.service.MyUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MyUserService myUserService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        if (request.getUsername() != null) {
            return loginWithUsername((AuthRequest) request);
        } else if (request.getEmail() != null) {
            return loginWithEmail((AuthRequest) request);
        } else {
            return ResponseEntity.badRequest().body(new AuthResponse("Invalid authentication request", null));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegistrationRequest request) {
        MyUserBuilder userBuilder = new MyUserBuilder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail());
        MyUser user = userBuilder.build();
        myUserService.save(user);
        try {
            String token = loginMethod(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new AuthResponse("User registered and logged in successfully", token));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new AuthResponse("User registered but failed to log in", null));
        }
    }

    private String loginMethod(String username, String password) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return jwtService.generateToken(username);
    }

    private ResponseEntity<?> loginWithEmail(AuthRequest request) {
        String username = myUserService.loadUserByEmail(request.getEmail()).getUsername();
        try {
            String token = loginMethod(username, request.getPassword());
            return ResponseEntity.ok(new AuthResponse("User logged in successfully", token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(new AuthResponse("Bad Credentials", null));
        }
    }

    private ResponseEntity<?> loginWithUsername(AuthRequest request) {
        try {
            String token = loginMethod(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new AuthResponse("User logged in successfully", token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(new AuthResponse("Invalid username or password", null));
        }
    }
}
