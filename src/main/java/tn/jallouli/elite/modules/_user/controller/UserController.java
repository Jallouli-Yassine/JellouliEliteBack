package tn.jallouli.elite.modules._user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.jallouli.elite.modules._user.entity.UserEntity;
import tn.jallouli.elite.modules._user.service.UserInterface;
import tn.jallouli.elite.modules._user.service.impl.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserInterface userInterface;
    public UserController(UserServiceImpl userServiceImpl) {
        this.userInterface = userServiceImpl;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/getAll")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.of(userInterface.getAllUsers());
    }

}