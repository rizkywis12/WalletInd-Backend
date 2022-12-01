package net.mujoriwi.walletind.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    private String notes;

    private Boolean status;

    private Boolean transactionCategory;

    private String transactionType;

    private LocalDateTime transactionCreated;

    // private LocalDateTime transactionUpdated;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User senderId;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiverId;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscriptionId;

    public Transaction(Long amount, String notes, User senderId, User receiverId, String transactionType,
            Boolean status,
            LocalDateTime transactionCreated, Boolean transactionCategory) {
        this.amount = amount;
        this.notes = notes;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.transactionType = transactionType;
        this.status = status;
        this.transactionCreated = transactionCreated;
        this.transactionCategory = transactionCategory;
    }

    public Transaction(Long amount, User receiverId) {
        this.amount = amount;
        this.receiverId = receiverId;
    }
}
