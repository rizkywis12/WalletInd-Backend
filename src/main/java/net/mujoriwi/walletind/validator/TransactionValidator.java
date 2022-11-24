package net.mujoriwi.walletind.validator;

import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.exception.custom.CustomBadRequestException;

@Service
public class TransactionValidator {
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
