package net.mujoriwi.walletind.validator;

import net.mujoriwi.walletind.exception.custom.CustomNotFoundException;
import net.mujoriwi.walletind.model.entity.Pin;
import net.mujoriwi.walletind.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PinValidator {
    public void validatePinExist(Optional<Pin> pinOpt) throws Exception {
        if (pinOpt.isPresent()) {
            throw new CustomNotFoundException("PIN already exist");
        }
    }
}
