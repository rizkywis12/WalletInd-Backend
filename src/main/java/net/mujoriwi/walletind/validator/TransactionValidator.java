package net.mujoriwi.walletind.validator;

import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.exception.custom.CustomBadRequestException;

@Service
public class TransactionValidator {
    public void validateBalanceEnough(Long amountRequest, Long amountSender) throws Exception {
        if (amountRequest > amountSender) {
            throw new CustomBadRequestException("Balance not enough");
        }
    }
}
