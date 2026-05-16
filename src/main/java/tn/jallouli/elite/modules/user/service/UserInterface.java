package tn.jallouli.elite.modules.user.service;

import org.springframework.web.multipart.MultipartFile;
import tn.jallouli.elite.modules.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserInterface {
    Optional<List<UserEntity>> getAllUsers();
    Optional<UserEntity> getUserByEmail(String email);
    String uploadProfileImage(Long userId, MultipartFile file);

}
