package net.mujoriwi.walletind.repository;

import net.mujoriwi.walletind.model.entity.User;
import net.mujoriwi.walletind.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
  UserRole findByUser(User user);
}
