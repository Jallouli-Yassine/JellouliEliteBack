package tn.jallouli.elite.modules._user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.jallouli.elite.modules._user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    @Query("select u from UserEntity u where u.resetPasswordToken = ?1")
    Optional<UserEntity> findByResetPasswordToken(String resetPasswordToken);
}