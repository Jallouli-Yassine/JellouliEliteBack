package tn.jallouli.elite.modules.userRole.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.jallouli.elite.modules.userRole.entity.RoleEntity;
import tn.jallouli.elite.modules.user.entity.RoleName;

public interface RoleRepo extends JpaRepository<RoleEntity,Long> {
    RoleEntity findByRoleName(RoleName roleName);

    boolean existsByRoleName(RoleName roleName);
}
