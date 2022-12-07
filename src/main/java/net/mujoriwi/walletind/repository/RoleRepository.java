package net.mujoriwi.walletind.repository;

import net.mujoriwi.walletind.model.entity.ERole;
import net.mujoriwi.walletind.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findByRoleName(ERole roleName);
}
