package net.mujoriwi.walletind.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.mujoriwi.walletind.model.entity.Transaction;
import net.mujoriwi.walletind.model.entity.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByTransactionCategoryAndSenderId(Boolean transactionCategory, User user);

    // List<Transaction>
    // findAllByTransactionCategoryAndSenderIdOrfindAllByTransactionCategoryAndReceiverId(
    // Boolean transactionCategory, User user, Boolean transactionCategory1, User
    // user1);

    List<Transaction> findAllByTransactionCategoryAndReceiverId(Boolean transactionCategory, User user);

    List<Transaction> findAllBySenderIdOrReceiverId(User sender, User receiver);
}
