package net.mujoriwi.walletind.validator;

import java.util.Optional;

import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.exception.custom.CustomNotFoundException;
import net.mujoriwi.walletind.exception.custom.CustomUnauthorizedException;
import net.mujoriwi.walletind.model.entity.User;

@Service
public class UserValidator {
    public void validateUserNotFound(Optional<User> userOpt) throws Exception {
        if (userOpt.isEmpty()) {
            throw new CustomNotFoundException("User not found. Please input correct user");
        }
    }

    public void validateUserFound(Optional<User> userOpt) throws Exception {
        if (userOpt.isPresent()) {
            throw new CustomNotFoundException("User is found. Please login!");
        }
    }

    public void validatePassword(String passwordDB, String requestDB) throws Exception {
        if (!passwordDB.equals(requestDB)) {
            throw new CustomUnauthorizedException("Unauthorized! Password does not match!");
        }
    }
}