package tn.jallouli.elite.modules.auth.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.jallouli.elite.modules._UserRole.Repository.RoleRepo;
import tn.jallouli.elite.modules._UserRole.entity.RoleEntity;
import tn.jallouli.elite.modules._user.entity.RoleName;
import tn.jallouli.elite.modules._user.entity.UserEntity;
import tn.jallouli.elite.modules._user.repository.UserRepository;
import tn.jallouli.elite.modules.auth.dto.AuthResponse;
import tn.jallouli.elite.modules.auth.dto.LoginRequest;
import tn.jallouli.elite.modules.auth.service.AuthInterface;
import tn.jallouli.elite.security.JWTGenerator;

@Service
public class AuthServiceImpl implements AuthInterface {
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JWTGenerator jwtGenerator;
	private final RoleRepo roleRepo;

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

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepo, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator, RoleRepo roleRepo) {
		this.authenticationManager = authenticationManager;
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtGenerator = jwtGenerator;
		this.roleRepo = roleRepo;
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
				newAdmin.setRole(newRole);
				userRepo.save(newAdmin);
			} else {
				newAdmin.setRole(adminRole);
				userRepo.save(newAdmin);
			}
		}
	}
}
