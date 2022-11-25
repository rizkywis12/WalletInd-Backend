package net.mujoriwi.walletind.service.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.model.dto.request.TransferDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.model.entity.TopUp;
import net.mujoriwi.walletind.model.entity.Transaction;
import net.mujoriwi.walletind.model.entity.User;
import net.mujoriwi.walletind.repository.TopUpRepository;
import net.mujoriwi.walletind.repository.TransactionRepository;
import net.mujoriwi.walletind.repository.UserRepository;
import net.mujoriwi.walletind.service.service.TransactionService;
import net.mujoriwi.walletind.validator.TopUpValidator;
import net.mujoriwi.walletind.validator.TransactionValidator;
import net.mujoriwi.walletind.validator.UserValidator;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopUpRepository topUpRepository;

    private User sender;
    private User receiver;
    private User user;
    private TopUp topUp;

    private Transaction transaction;
    private Transaction transaction2;

    private ResponseData<Object> responseData;

    @Autowired
    private TransactionValidator transactionValidator;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private TopUpValidator topUpValidator;

    private Map<Object, Object> data;

    private List<Transaction> transactions;
    List<Map<Object, Object>> maps;

    void transactionInformation() {
        data = new HashMap<>();
        if (transaction.getSenderId() == null) {
            data.put("Top Up From", transaction.getTopUpId().getPaymentName());
        } else {
            data.put("Sender", transaction.getSenderId().getUserName());
        }

        data.put("Receiver", transaction.getReceiverId().getUserName());

        if (transaction.getSenderId() != null) {
            data.put("Sender Balance", transaction.getSenderId().getBalance());
        }

        data.put("Receiver Balance", transaction.getReceiverId().getBalance());
        data.put("Status", transaction.getStatus());
        data.put("Transaction Type", transaction.getTransactionType());
        data.put("Timestamp", transaction.getTransactionCreated());
    }

    @Override
    public ResponseData<Object> addTransfer(Long senderId, Long receiverId, TransferDto request) throws Exception {
        Optional<User> senderIdOpt = userRepository.findById(senderId);
        Optional<User> receiverIdOpt = userRepository.findById(receiverId);

        userValidator.validateUserNotFound(senderIdOpt);
        userValidator.validateUserNotFound(receiverIdOpt);

        sender = senderIdOpt.get();
        receiver = receiverIdOpt.get();

        transactionValidator.validateSenderAndReceiver(sender, receiver);

        transactionValidator.validateMinimumAmount(request.getAmount());

        transaction = new Transaction(request.getAmount(), request.getNotes(), sender, receiver, "Transfer", true,
                LocalDateTime.now(), false);
        transaction2 = new Transaction(request.getAmount(), request.getNotes(), sender, receiver, "Transfer", true,
                LocalDateTime.now(), true);

        transactionValidator.validateBalanceEnough(request.getAmount(), sender.getBalance());

        sender.setBalance(sender.getBalance() - request.getAmount());
        receiver.setBalance(receiver.getBalance() + request.getAmount());

        userRepository.save(sender);
        userRepository.save(receiver);
        transactionRepository.save(transaction);
        transactionRepository.save(transaction2);

        transactionInformation();

        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Success add transfer", data);

        return responseData;
    }

    @Override
    public ResponseData<Object> addTopUp(Long topUpid, Long receiverId, TransferDto request) throws Exception {
        Optional<User> receiverIdOpt = userRepository.findById(receiverId);
        Optional<TopUp> topUpIdOpt = topUpRepository.findById(topUpid);

        topUpValidator.validateTopUpNotFound(topUpIdOpt);

        userValidator.validateUserNotFound(receiverIdOpt);

        receiver = receiverIdOpt.get();

        topUp = topUpIdOpt.get();

        transaction = new Transaction(request.getAmount(), request.getNotes(), receiver, topUp);
        transaction.setTransactionType("TopUp");
        receiver.setBalance(receiver.getBalance() + request.getAmount());
        transaction.setTransactionCreated(LocalDateTime.now());
        transaction.setStatus(true);
        transaction.setTransactionCategory(true);

        userRepository.save(receiver);
        transactionRepository.save(transaction);

        transactionInformation();

        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Success Top Up", data);
        return responseData;
    }

    @Override
    public ResponseData<Object> getTransferCategory(Long userId, Boolean transactionCategory) throws Exception {
        Optional<User> userIdOpt = userRepository.findById(userId);

        userValidator.validateUserNotFound(userIdOpt);

        user = userIdOpt.get();

        // Show Transaction Category (true = incomes, false = expenses, null = all
        // transaction history)
        if (transactionCategory == null) {
            transactions = transactionRepository.findAllBySenderIdOrReceiverId(user, user);
        } else if (transactionCategory == false) {
            transactions = transactionRepository.findAllByTransactionCategoryAndSenderId(transactionCategory, user);
        } else if (transactionCategory == true) {
            transactions = transactionRepository.findAllByTransactionCategoryAndReceiverId(transactionCategory, user);
        }

        transactionValidator.validateNoTransactions(transactions);

        maps = new ArrayList<Map<Object, Object>>();
        for (int i = 0; i < transactions.size(); i++) {
            transaction = transactions.get(i);
            transactionInformation();
            maps.add(data);
        }

        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Success", maps);
        return responseData;
    }

}
