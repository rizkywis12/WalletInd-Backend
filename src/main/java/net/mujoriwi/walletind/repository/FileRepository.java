package net.mujoriwi.walletind.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.mujoriwi.walletind.model.entity.FileEntity;
import net.mujoriwi.walletind.model.entity.User;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {
    Optional<FileEntity> findByUserId(User user);
}
