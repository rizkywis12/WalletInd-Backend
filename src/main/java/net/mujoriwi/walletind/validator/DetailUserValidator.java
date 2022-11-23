package net.mujoriwi.walletind.validator;

import java.util.Optional;

import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.exception.custom.CustomNotFoundException;
import net.mujoriwi.walletind.model.entity.DetailUser;

@Service
public class DetailUserValidator {
    public void validateUserIdExist(Optional<DetailUser> detailUserOpt) throws Exception {
        if (detailUserOpt.isPresent()) {
            throw new CustomNotFoundException("Biodata already exist, kindly check");
        }
    }
}
