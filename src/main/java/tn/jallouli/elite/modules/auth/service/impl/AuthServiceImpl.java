package tn.jallouli.elite.modules.auth.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.jallouli.elite.exception.BusinessException;
import tn.jallouli.elite.exception.ResourceNotFoundException;
import tn.jallouli.elite.modules.userRole.Repository.RoleRepo;
import tn.jallouli.elite.modules.userRole.entity.RoleEntity;
import tn.jallouli.elite.modules.user.entity.RoleName;
import tn.jallouli.elite.modules.user.entity.UserEntity;
import tn.jallouli.elite.modules.user.repository.UserRepository;
import tn.jallouli.elite.modules.user.service.email.EmailService;
import tn.jallouli.elite.modules.auth.dto.*;
import tn.jallouli.elite.modules.auth.service.AuthInterface;
import tn.jallouli.elite.security.JWTGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthInterface {
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JWTGenerator jwtGenerator;
	private final RoleRepo roleRepo;
	private final EmailService emailService;

	@Value("${app.bootstrap.admin.username:admin}")
	private String adminUsername;

	@Value("${app.bootstrap.admin.password:admin}")
	private String adminPassword;

	@Value("${app.bootstrap.admin.email:admin@demo.com}")
	private String adminEmail;

	@Value("${app.bootstrap.admin.phone:00000000}")
	private String adminPhone;

	@Value("${app.bootstrap.admin.first-name:Admin}")
	private String adminFirstName;

	@Value("${app.bootstrap.admin.last-name:Demo}")
	private String adminLastName;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepo, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator, RoleRepo roleRepo, EmailService emailService) {
		this.authenticationManager = authenticationManager;
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtGenerator = jwtGenerator;
		this.roleRepo = roleRepo;
        this.emailService = emailService;
    }

	@Override
	public AuthResponse login(LoginRequest loginDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);
		AuthResponse authResponseDTO = new AuthResponse();
		authResponseDTO.setAccessToken(token);
		return authResponseDTO;
	}

	@Override
	@Transactional
	public void createDefaultAdminAccount() {
		if (!userRepo.existsByUsername(adminUsername)) {
			UserEntity newAdmin = new UserEntity();
			newAdmin.setUsername(adminUsername);
			newAdmin.setPassword(passwordEncoder.encode(adminPassword));
			newAdmin.setEmail(adminEmail);
			newAdmin.setPhone(adminPhone);
			newAdmin.setFirstName(adminFirstName);
			newAdmin.setLastName(adminLastName);
			RoleEntity adminRole = roleRepo.findByRoleName(RoleName.ADMIN);
			if (adminRole == null) {
				RoleEntity newRole = new RoleEntity();
				newRole.setRoleName(RoleName.ADMIN);
				roleRepo.save(newRole);
				newAdmin.getRoles().add(newRole);
				userRepo.save(newAdmin);
			} else {
				newAdmin.getRoles().add(adminRole);
				userRepo.save(newAdmin);
			}
		}
	}

	@Override
	@Transactional
	public void register(RegisterRequest newUser) {
		if (userRepo.existsByEmail(newUser.getEmail())) {
			throw new BusinessException("Email already in use");
		}

		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName(newUser.getFirstName());
		userEntity.setLastName(newUser.getLastName());

		// Sécurité: Si le username est vide dans la requête, on utilise l'email
		userEntity.setUsername(newUser.getUsername());
		userEntity.setEmail(newUser.getEmail());
		userEntity.setPhone(newUser.getPhone());
		userEntity.setPassword(passwordEncoder.encode(newUser.getPassword()));

		// Rôle par défaut générique
		RoleName targetRole = newUser.getRoleName() != null ? newUser.getRoleName() : RoleName.STUDENT;
		RoleEntity userRole = roleRepo.findByRoleName(targetRole);

		if (userRole == null) {
			userRole = new RoleEntity();
			userRole.setRoleName(targetRole);
			roleRepo.save(userRole);
		}

		// On lie le rôle une seule fois
		userEntity.getRoles().add(userRole);
		// On sauvegarde l'utilisateur une seule fois à la fin !
		userRepo.save(userEntity);

		emailService.sendWelcomeEmail(newUser.getEmail(), newUser.getFirstName());
	}

	@Override
	@Transactional
	public void forgotPassword(ForgotPasswordRequest request) {
		// 1. Chercher l'utilisateur par email
		UserEntity user = userRepo.findByEmail(request.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("Aucun compte avec cet email."));

		// 2. Générer un token unique et sa date d'expiration (ex: valable 30 minutes)
		String token = UUID.randomUUID().toString();
		user.setResetPasswordToken(token);
		user.setResetPasswordTokenExpiry(LocalDateTime.now().plusMinutes(30));

		userRepo.save(user);

		// 3. Construire le lien de réinitialisation (Ceci doit pointer vers votre front-end : Angular / React)
		String frontendUrl = "http://localhost:4200";
		String resetUrl = frontendUrl + "/reset-password?token=" + token;

		// 4. Envoyer l'email avec la fonction que vous avez préparée
		emailService.sendPasswordResetEmail(user.getEmail(), user.getFirstName(), resetUrl);
	}

	@Override
	@Transactional
	public void resetPassword(ResetPasswordRequest request) {
		// 1. Chercher l'utilisateur avec ce token
		UserEntity user = userRepo.findByResetPasswordToken(request.getToken())
				.orElseThrow(() -> new BusinessException("Token invalide ou introuvable."));

		// 2. Vérifier si le token est expiré
		if (user.getResetPasswordTokenExpiry().isBefore(LocalDateTime.now())) {
			throw new BusinessException("Le token a expiré. Veuillez refaire une demande.");
		}

		// 3. Mettre à jour le mot de passe et réinitialiser les champs du token
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		user.setResetPasswordToken(null);
		user.setResetPasswordTokenExpiry(null);
		userRepo.save(user);

		// 4. Envoyer l'email de succès
		emailService.sendPasswordUpdateSuccessEmail(user.getEmail(), user.getFirstName());
	}


	// 2. Boucle au lieu de copier-coller = Code plus propre
	@Override
	@Transactional
	public void createRoles() {
		for (RoleName roleName : RoleName.values()) {
			if (!roleRepo.existsByRoleName(roleName)) {
				RoleEntity role = new RoleEntity();
				role.setRoleName(roleName);
				roleRepo.save(role);
			}
		}
	}
}
