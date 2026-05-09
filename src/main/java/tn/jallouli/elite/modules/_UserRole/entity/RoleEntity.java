package tn.jallouli.elite.modules._UserRole.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tn.jallouli.elite.modules._user.entity.RoleName;

@Entity
@Getter
@Setter
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleName roleName;


}
