package tn.jallouli.elite.modules._UserRole.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.jallouli.elite.modules._UserRole.entity.RoleEntity;
import tn.jallouli.elite.modules._user.entity.RoleName;

public interface RoleRepo extends JpaRepository<RoleEntity,Long> {
    RoleEntity findByRoleName(RoleName roleName);

}
