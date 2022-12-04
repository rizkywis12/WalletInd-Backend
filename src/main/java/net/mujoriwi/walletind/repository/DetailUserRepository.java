package net.mujoriwi.walletind.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.mujoriwi.walletind.model.entity.DetailUser;
import net.mujoriwi.walletind.model.entity.User;

@Repository
public interface DetailUserRepository extends JpaRepository<DetailUser, Long> {
    Optional<DetailUser> findByUserId(User user);

    @Query(value = "SELECT * FROM detail_users WHERE user_id = ?1", nativeQuery = true)
    Optional<DetailUser> findByIdUser(Long id);

    Optional<DetailUser> findByUser(User user);
}
