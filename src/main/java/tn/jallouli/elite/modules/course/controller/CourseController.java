package tn.jallouli.elite.modules.course.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.jallouli.elite.exception.BusinessException;
import tn.jallouli.elite.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/courses")
public class CourseController {

    // ==== EXEMPLE TRÈS SIMPLE POUR TON E-LEARNING ====

    // 1. Gérer un cours introuvable
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id) {
        // Imaginons que la base de données ne trouve pas le cours n°10
        boolean courseExists = false;

        if (!courseExists) {
            // C'est tout ! Le GlobalExceptionHandler s'occupe de générer la belle erreur JSON (Statut 404)
            throw new ResourceNotFoundException("Le cours avec l'ID " + id + " n'existe pas dans notre catalogue.");
        }

        return ResponseEntity.ok("Cours trouvé !");
    }

    // 2. Gérer une règle métier (ex: l'étudiant est déjà inscrit, ou cours payant sans abonnement)
    @PostMapping("/{id}/enroll")
    public ResponseEntity<?> enrollInCourse(@PathVariable Long id) {
        // Imaginons que l'étudiant essaie de s'inscrire à un cours qu'il possède déjà
        boolean alreadyEnrolled = true;

        if (alreadyEnrolled) {
            // On lance l'erreur métier. Le GlobalExceptionHandler en fera une erreur JSON (Statut 400)
            throw new BusinessException("Vous êtes déjà inscrit à ce cours ! Allez dans 'Mes Cours' pour commencer à apprendre.");
        }

        return ResponseEntity.ok("Inscription réussie !");
    }
}
