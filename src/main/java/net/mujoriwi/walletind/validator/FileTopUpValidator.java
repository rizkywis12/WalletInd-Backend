package net.mujoriwi.walletind.validator;

import java.util.Optional;

import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.exception.custom.CustomNotFoundException;
import net.mujoriwi.walletind.model.entity.FileTopUp;

@Service
public class FileTopUpValidator {
    public void validateTopUpIdExist(Optional<FileTopUp> fileTopUpOpt) throws Exception {
        if (fileTopUpOpt.isPresent()) {
            throw new CustomNotFoundException("Top up already exist");
        }
    }

    public void validateTopUpIdNotExist(Optional<FileTopUp> fileTopUpOpt) throws Exception {
        if (fileTopUpOpt.isEmpty()) {
            throw new CustomNotFoundException("Top up not exist");
        }
    }
}
