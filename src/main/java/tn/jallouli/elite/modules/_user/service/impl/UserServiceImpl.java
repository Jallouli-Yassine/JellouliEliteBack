package tn.jallouli.elite.modules._user.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.jallouli.elite.exception.BusinessException;
import tn.jallouli.elite.exception.ResourceNotFoundException;
import tn.jallouli.elite.modules._user.entity.UserEntity;
import tn.jallouli.elite.modules._user.repository.UserRepository;
import tn.jallouli.elite.modules._user.service.UserInterface;
import tn.jallouli.elite.modules._user.service.uplaodFile.CloudinaryService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserInterface {

    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    public UserServiceImpl(UserRepository userRepository, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public Optional<List<UserEntity>> getAllUsers() {
        return Optional.of(userRepository.findAll());
    }

    // N'oubliez pas le private final CloudinaryService cloudinaryService; dans le constructeur

    @Override
    public String uploadProfileImage(Long userId, MultipartFile file) {
        // 1. Vérifier que le fichier n'est pas vide (Optionnel mais recommandé)
        if (file.isEmpty()) {
            throw new BusinessException("Le fichier image est vide.");
        }

        // 2. Chercher l'utilisateur
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable."));

        // 3. Uploader l'image dans un dossier "elite_profiles" sur Cloudinary
        String imageUrl = cloudinaryService.uploadFile(file, "elite_profiles");

        // 4. Mettre à jour l'entité et sauvegarder
        user.setImage(imageUrl);
        userRepository.save(user);

        return imageUrl;
    }
}
