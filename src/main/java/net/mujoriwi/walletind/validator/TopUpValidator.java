package net.mujoriwi.walletind.validator;

import java.util.Optional;

import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.exception.custom.CustomNotFoundException;
import net.mujoriwi.walletind.model.entity.TopUp;

@Service
public class TopUpValidator {
    public void validatePaymentNameExist(Optional<TopUp> topUpOpt) throws Exception {
        if (topUpOpt.isPresent()) {
            throw new CustomNotFoundException("payment already exist. ");
        }
    }

    public void validateTopUpNotFound( Optional<TopUp> topUpIdOpt) throws Exception {
        if (topUpIdOpt.isEmpty()) {
            throw new CustomNotFoundException("Payment not found. Please input correct payment");
        }
    }
}
