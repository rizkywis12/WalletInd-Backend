package net.mujoriwi.walletind.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.mujoriwi.walletind.model.entity.FileTopUp;
import net.mujoriwi.walletind.model.entity.TopUp;

public interface FileTopUpRepository extends JpaRepository<FileTopUp, String> {
    Optional<FileTopUp> findByTopUpId(TopUp topUp);

    @Query(value = "SELECT * FROM files_top_up where top_up_id = ?1", nativeQuery = true)
    Optional<FileTopUp> findTopUpId(Long id);
}
