package net.mujoriwi.walletind.validator;

import java.util.Optional;

import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.exception.custom.CustomBadRequestException;
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

    public void validateEmailExist(Optional<User> emailOpt) throws Exception {
        if (emailOpt.isPresent()) {
            throw new CustomNotFoundException("Email already exist. Please login!");
        }
    }

    public void validateUserNameExist(Optional<User> userNameOpt) throws Exception {
        if (userNameOpt.isPresent()) {
            throw new CustomNotFoundException("Username already exist. Please login!");
        }
    }

    public void validatePassword(String passwordDB, String requestDB) throws Exception {
        if (!passwordDB.equals(requestDB)) {
            throw new CustomUnauthorizedException("Unauthorized! Password does not match!");
        }
    }
    public void validateCurrentPassword(String passwordDB, String requestDB) throws Exception {
        if (!passwordDB.equals(requestDB)) {
            throw new CustomUnauthorizedException("Unauthorized! Please Input Current Password");
        }
    }

    public void validateConfirmPassword(String requestPass, String requestConfirmPass) throws Exception {
        if (!requestPass.equals(requestConfirmPass)) {
            throw new CustomBadRequestException("The password confirmation not match!");
        }
    }

    public void validateForgotPassword(String passDB, String requestPass) throws Exception {
        if (passDB.equals(requestPass)) {
            throw new CustomBadRequestException("Your new password cannot be the same as your current password!");
        }
    }
}