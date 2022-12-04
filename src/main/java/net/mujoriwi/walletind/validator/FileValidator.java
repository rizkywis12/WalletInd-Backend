package net.mujoriwi.walletind.validator;

import java.util.Optional;

import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.exception.custom.CustomNotFoundException;
import net.mujoriwi.walletind.model.entity.FileEntity;

@Service
public class FileValidator {
    public void validateUserIdExist(Optional<FileEntity> fileEntityOpt) throws Exception {
        if (fileEntityOpt.isPresent()) {
            throw new CustomNotFoundException("Image already exist");
        }
    }
    public void validateFileNotFound(Optional<FileEntity> fileEntityOpt) throws Exception {
        if (fileEntityOpt.isEmpty()) {
            throw new CustomNotFoundException("Biodata Not Found, Please Add Bio First!");
        }
    }
}
