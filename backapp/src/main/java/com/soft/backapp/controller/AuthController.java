package com.soft.backapp.controller;

import java.util.Calendar;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soft.backapp.dto.AuthRequest;
import com.soft.backapp.dto.AuthResponse;
import com.soft.backapp.dto.PasswordChangeRequest;
import com.soft.backapp.dto.RegistrationRequest;
import com.soft.backapp.dto.ResetPasswordRequest;
import com.soft.backapp.model.MyUser;
import com.soft.backapp.model.MyUserBuilder;
import com.soft.backapp.model.PasswordReset;
import com.soft.backapp.security.JwtService;
import com.soft.backapp.service.MailService;
import com.soft.backapp.service.MyUserService;
import com.soft.backapp.service.PasswordResetService;
import com.soft.backapp.utils.Functions;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MyUserService myUserService;
    private final MailService mailService;
    private final PasswordResetService passwordResetService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        if (request.getUsername() != null) {
            return loginWithUsername((AuthRequest) request);
        } else if (request.getEmail() != null) {
            return loginWithEmail((AuthRequest) request);
        } else {
            return ResponseEntity.badRequest().body(new AuthResponse("Invalid authentication request"));
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
            return ResponseEntity.ok(new AuthResponse("User registered and logged in successfully", token, myUserService.getUserByUsername(request.getUsername()).getId()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new AuthResponse("User registered but failed to log in"));
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest request){
        try {
                String username = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

            MyUser user = myUserService.getUserByUsername(username);

            // verify old password
            if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                throw new RuntimeException("Old password is incorrect");
            }

            // encode new password
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            myUserService.save(user);
            return ResponseEntity.ok(new AuthResponse("password changed successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new AuthResponse(e.getMessage()));
        }
    }

    @GetMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestParam String email) {
        String code = Functions.GenerateForgetPasswordCode();
        PasswordReset passwordReset = new PasswordReset(email,passwordEncoder.encode(code),Calendar.getInstance().getTime());
        mailService.sendForgetPasswordMail(email,myUserService.getUserByEmail(email).get().getFirstName(),code);
        passwordResetService.savePasswordReset(passwordReset);
        return ResponseEntity.ok(new AuthResponse("password reset mail sent"));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request){
        try {
            PasswordReset passwordReset = passwordResetService.getByEmail(request.getEmail()).get();
            if (!passwordEncoder.matches(request.getCode(), passwordReset.getCode())) {
                throw new RuntimeException("Wrong code");
            }

            MyUser user = myUserService.getUserByEmail(request.getEmail()).get();
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            myUserService.save(user);
            passwordResetService.deletePasswordReset(passwordReset.getId());

            return ResponseEntity.ok(new AuthResponse("Password reset successful"));
            
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new AuthResponse(e.getMessage()));
            
        }
    }
    

    private String loginMethod(String username, String password) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return jwtService.generateToken(myUserService.getUserByUsername(username));
    }

    private ResponseEntity<?> loginWithEmail(AuthRequest request) {
        String username = myUserService.loadUserByEmail(request.getEmail()).getUsername();
        try {
            String token = loginMethod(username, request.getPassword());
            return ResponseEntity.ok(new AuthResponse("User logged in successfully", token, myUserService.getUserByUsername(username).getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(new AuthResponse("Bad Credentials"));
        }
    }

    private ResponseEntity<?> loginWithUsername(AuthRequest request) {
        try {
            String token = loginMethod(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new AuthResponse("User logged in successfully", token, myUserService.getUserByUsername(request.getUsername()).getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(new AuthResponse("Invalid username or password"));
        }
    }
}
