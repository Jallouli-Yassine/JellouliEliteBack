package tn.jallouli.elite.modules._user.entity;

import jakarta.persistence.*;
import lombok.*;
import tn.jallouli.elite.modules._UserRole.entity.RoleEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idUser;

    private String firstName;

    private String lastName;

    @Column(unique = true ,nullable = false)
    private String phone;

    @Column(unique = true ,nullable = false)
    private String email;

    private String image;

    private String password;

    private String username;

    @ManyToOne
    private RoleEntity role;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;



}
