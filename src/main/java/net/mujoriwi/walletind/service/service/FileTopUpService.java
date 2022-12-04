package net.mujoriwi.walletind.service.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import net.mujoriwi.walletind.model.entity.FileTopUp;

public interface FileTopUpService {
    // FileTopUp updateFile(MultipartFile file, Long topUp_id, String id) throws
    // IOException, Exception;

    FileTopUp store(MultipartFile file, Long topUp_id) throws IOException, Exception;

    FileTopUp getFile(Long id) throws Exception;

    FileTopUp updateFile(MultipartFile file, Long topUp_id) throws IOException, Exception;

    // Stream<FileTopUp> getFiles();
}
