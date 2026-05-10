package tn.jallouli.elite.modules.auth.controller;


import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.jallouli.elite.modules.auth.dto.*;
import tn.jallouli.elite.modules.auth.service.AuthInterface;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
public class AuthController {
    private final AuthInterface authInterface;

    public AuthController(AuthInterface authInterface) {
        this.authInterface = authInterface;
    }


    //execute lorsque lapp se lance pour creer les roles dans la base de données

    @PostConstruct
    public void createDefaultAdminAccount(){
        authInterface.createDefaultAdminAccount();
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginDTO){
        AuthResponse response = authInterface.login(loginDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest newUser){
        authInterface.register(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authInterface.forgotPassword(request);
        // Toujours renvoyer un succès même si l'email n'existe pas pour des raisons de sécurité
        // (pour ne pas permettre à un hacker de la deviner), ou laissez l'exception remonter, au choix !
        return ResponseEntity.ok(Map.of("message", "Si cet email existe, un lien de réinitialisation a été envoyé."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authInterface.resetPassword(request);
        return ResponseEntity.ok(Map.of("message", "Mot de passe réinitialisé avec succès."));
    }
}