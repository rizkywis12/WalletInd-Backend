package net.mujoriwi.walletind.validator;

import java.util.List;

import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.exception.custom.CustomBadRequestException;
import net.mujoriwi.walletind.exception.custom.CustomNotFoundException;
import net.mujoriwi.walletind.model.entity.Transaction;
import net.mujoriwi.walletind.model.entity.User;

@Service
public class TransactionValidator {
    public void validateSenderAndReceiver(User sender, User receiver) throws Exception {
        if (sender == receiver) {
            throw new CustomBadRequestException("Same user receiver. Please input different user!");
        }
    }

    public void validateBalanceEnough(Long amountRequest, Long balanceSender) throws Exception {
        if (amountRequest > balanceSender - 50000) {
            throw new CustomBadRequestException("Balance is not enough. Your balance must be at least 50000!");
        }
    }

    public void validateMinimumAmount(Long amountRequest) throws Exception {
        if (amountRequest < 50000) {
            throw new CustomBadRequestException("Minimum transfer = 50000");
        }
    }

    public void validateNoTransactions(List<Transaction> transactions) throws Exception {
        if (transactions.isEmpty()) {
            throw new CustomNotFoundException("The user has not made any transactions!");
        }
    }
}
