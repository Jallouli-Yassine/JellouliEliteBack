package tn.jallouli.elite.modules.auth.controller;


import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.jallouli.elite.modules.auth.dto.AuthResponse;
import tn.jallouli.elite.modules.auth.dto.LoginRequest;
import tn.jallouli.elite.modules.auth.service.AuthInterface;

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
}