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

import net.mujoriwi.walletind.model.dto.request.TopUpDto;
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

    public void list() {
        maps = new ArrayList<Map<Object, Object>>();
        for (int i = 0; i < transactions.size(); i++) { 
            transaction = transactions.get(i);
            transferInformation();
            maps.add(data);
        }
    }

    void transferInformation() {
        data = new HashMap<>();
        data.put("Sender", transaction.getSenderId().getUserName());
        data.put("Receiver", transaction.getReceiverId().getUserName());
        data.put("Sender Balance", transaction.getSenderId().getBalance());
        data.put("Receiver Balance", transaction.getReceiverId().getBalance());
        data.put("Status", transaction.getStatus());
        data.put("Transaction Type", transaction.getTransactionType());
        data.put("Timestamp", transaction.getTransactionCreated());
    }

    void topUpInformation() {
        data = new HashMap<>();
        data.put("Top Up From", transaction.getTopUpId().getPaymentName());
        data.put("Receiver", transaction.getReceiverId().getUserName());
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

        transaction = new Transaction(request.getAmount(), request.getNotes(), sender, receiver);
        transaction.setTransactionType("Transfer");

        transactionValidator.validateBalanceEnough(request.getAmount(), sender.getBalance());

        sender.setBalance(sender.getBalance() - request.getAmount());
        receiver.setBalance(receiver.getBalance() + request.getAmount());

        transaction.setTransactionCreated(LocalDateTime.now());
        transaction.setStatus(true);
        userRepository.save(sender);
        userRepository.save(receiver);
        transactionRepository.save(transaction);

        transferInformation();

        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Success add transfer", data);

        return responseData;
    }

    @Override
    public ResponseData<Object> addTopUp( Long topUpid , Long receiverId , TransferDto request) throws Exception {
        Optional<User> receiverIdOpt = userRepository.findById(receiverId);
        Optional<TopUp> topUpIdOpt = topUpRepository.findById(topUpid);
        topUpValidator.validateTopUpNotFound(topUpIdOpt);
        userValidator.validateUserNotFound(receiverIdOpt);
        receiver = receiverIdOpt.get();
        topUp = topUpIdOpt.get();
        transaction = new Transaction(request.getAmount(), request.getNotes(),  receiver, topUp);
        transaction.setTransactionType("TopUp");
        receiver.setBalance(receiver.getBalance() + request.getAmount());
        transaction.setTransactionCreated(LocalDateTime.now());
        transaction.setStatus(false);
        userRepository.save(receiver);
        transactionRepository.save(transaction);
        topUpInformation();
        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Success Top Up", data);
        return responseData;
    }

    // Find transfer income or transfer expense
    @Override
    public ResponseData<Object> getTransferCategory(Long userId, Boolean status) throws Exception {
        Optional<User> userIdOpt = userRepository.findById(userId);

        userValidator.validateUserNotFound(userIdOpt);

        user = userIdOpt.get();

        if (status == null) {
            transactions = transactionRepository.findAll();
        } else if (status == true) {
            // When current user being sender then it will be expenses
            transactions = transactionRepository.findAllBySenderId(user);
        } else if (status == false) {
            // When current user being receiver then it will be incomes
            transactions = transactionRepository.findAllByReceiverId(user);
        }

        list();

        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "success", maps);
        return responseData;
    }

}
