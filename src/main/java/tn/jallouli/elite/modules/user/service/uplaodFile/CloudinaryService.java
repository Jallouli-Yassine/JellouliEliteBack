package tn.jallouli.elite.modules.user.service.uplaodFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.jallouli.elite.exception.BusinessException;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    /**
     * Méthode générique pour uploader n'importe quel fichier sur Cloudinary
     */
    public String uploadFile(MultipartFile file, String folderName) {
        try {
            // Options: on spécifie le dossier de destination sur Cloudinary
            Map<String, Object> uploadParams = ObjectUtils.asMap(
                    "folder", folderName
            );

            // Upload du fichier
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadParams);

            // Retourne le lien sécurisé (https) généré par Cloudinary
            return uploadResult.get("secure_url").toString();

        } catch (IOException e) {
            throw new BusinessException("Échec de l'upload de l'image sur le serveur.");
        }
    }
}