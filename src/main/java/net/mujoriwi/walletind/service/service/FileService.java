package net.mujoriwi.walletind.service.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import net.mujoriwi.walletind.model.entity.FileEntity;

public interface FileService {
    FileEntity updateFile(MultipartFile file,Long user_id, String id) throws IOException, Exception;
    FileEntity store(MultipartFile file,Long user_id) throws IOException, Exception;
    FileEntity getFile(String id);
}
