package net.mujoriwi.walletind.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.mujoriwi.walletind.model.entity.Transaction;
import net.mujoriwi.walletind.model.entity.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
        // @Query(value = "SELECT * FROM transactions WHERE (sender_id = ?1 AND
        // transaction_category = false) AND (transaction_created BETWEEN ?2 AND ?3)
        // ORDER BY DATE(transaction_created) DESC, transaction_created DESC",
        // nativeQuery = true)
        // List<Transaction> findHistory(User user, LocalDateTime transactionStart,
        // LocalDateTime transactionEnd);

        @Query(value = "SELECT * FROM transactions WHERE (sender_id = ?1 AND transaction_category = false AND transaction_created BETWEEN ?2 AND ?3) OR (receiver_id = ?1 AND transaction_category = true AND transaction_created BETWEEN ?2 AND ?3) ORDER BY DATE(transaction_created) DESC, transaction_created DESC", nativeQuery = true)
        List<Transaction> findHistory(User user, LocalDateTime transactionStart,
                        LocalDateTime transactionEnd);

        // @Query(value = "SELECT * FROM transactions WHERE (sender_id = ?1 AND
        // transaction_category = false AND transaction_created BETWEEN ?2 AND ?3) OR
        // (receiver_id = ?1 AND transaction_category = true AND transaction_created
        // BETWEEN ?2 AND ?3) ORDER BY DATE(transaction_created) ASC,
        // transaction_created ASC", nativeQuery = true)
        // List<Transaction> findHistoryAsc(User user, LocalDateTime transactionStart,
        // LocalDateTime transactionEnd);

        @Query(value = "SELECT * FROM transactions WHERE (sender_id = ?1 AND transaction_category = false) OR (receiver_id = ?1 AND transaction_category = true) ORDER BY DATE(transaction_created) ASC, transaction_created ASC", nativeQuery = true)
        List<Transaction> findHistoryAsc(User user);

        @Query(value = "SELECT * FROM transactions WHERE (sender_id = ?1 AND transaction_category = false) OR (receiver_id = ?1 AND transaction_category = true)", nativeQuery = true)
        List<Transaction> findSum(User user);
}
