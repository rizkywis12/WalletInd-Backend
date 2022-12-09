package net.mujoriwi.walletind.repository;

import net.mujoriwi.walletind.model.entity.Pin;
import net.mujoriwi.walletind.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PinRepository extends JpaRepository<Pin, Long> {
    Optional<Pin> findByUserId(User user);


}