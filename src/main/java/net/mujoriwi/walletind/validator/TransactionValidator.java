package net.mujoriwi.walletind.validator;

import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.exception.custom.CustomBadRequestException;
import net.mujoriwi.walletind.model.entity.User;

@Service
public class TransactionValidator {
    public void validateSenderAndReceiver(User sender, User receiver) throws Exception {
        if (sender == receiver) {
            throw new CustomBadRequestException("Same user receiver. Please input different user!");
        }
    }

    public void validateBalanceEnough(Long amountRequest, Long amountSender) throws Exception {
        if (amountRequest > amountSender - 50000) {
            throw new CustomBadRequestException("Balance is not enough. Your balance must be at least 50000!");
        }
    }

    public void validateMinimumAmount(Long amountRequest) throws Exception {
        if (amountRequest < 50000) {
            throw new CustomBadRequestException("Minimum transfer = 50000");
        }
    }
}
