package net.mujoriwi.walletind.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.mujoriwi.walletind.model.entity.TopUp;

public interface TopUpRepository extends JpaRepository<TopUp , Long > {
    Optional<TopUp> findByPaymentName(String paymentName);
    
}
