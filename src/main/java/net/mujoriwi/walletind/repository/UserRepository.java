package net.mujoriwi.walletind.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.mujoriwi.walletind.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users where id <> ?1", nativeQuery = true)
    List<User> findId(Long id);
}
