package net.mujoriwi.walletind.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.mujoriwi.walletind.model.entity.DetailUser;
import net.mujoriwi.walletind.model.entity.User;

@Repository
public interface DetailUserRepository extends JpaRepository<DetailUser, Long> {
    Optional<DetailUser> findByUserId(User user);
}
