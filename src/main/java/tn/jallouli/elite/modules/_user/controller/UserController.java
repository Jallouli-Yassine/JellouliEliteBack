package tn.jallouli.elite.modules._user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.jallouli.elite.modules._user.entity.UserEntity;
import tn.jallouli.elite.modules._user.service.UserInterface;
import tn.jallouli.elite.modules._user.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Map;

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
    @GetMapping("/getMe/{email}")
    public ResponseEntity<UserEntity> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.of(userInterface.getUserByEmail(email));
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<Map<String, String>> uploadImage(
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile file) {

        String imageUrl = userInterface.uploadProfileImage(id, file);

        return ResponseEntity.ok(Map.of(
                "message", "Image profil mise à jour avec succès.",
                "imageUrl", imageUrl
        ));
    }
}