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
    private Map<Object, Object> listData;

    private List<Transaction> transactions;

    private List<Map<Object, Object>> history;
    private List<Map<Object, Object>> sorted;

    private long sumIncome;

    private long sumExpense;

    void transactionInformation() {
        data = new HashMap<>();
        data.put("id", transaction.getId());
        if (transaction.getSenderId() == null) {
            data.put("sender", transaction.getTopUpId().getPaymentName());
        } else {
            data.put("sender", transaction.getSenderId().getUserName());
        }

        data.put("receiver", transaction.getReceiverId().getUserName());

        if (transaction.getSenderId() != null) {
            data.put("senderBalance", transaction.getSenderId().getBalance());
        }

        data.put("receiverBalance", transaction.getReceiverId().getBalance());
        data.put("amount", transaction.getAmount());
        // data.put("Status", transaction.getStatus());
        data.put("transactionType", transaction.getTransactionType());
        data.put("transactionCategory", transaction.getTransactionCategory());
        data.put("timestamp", transaction.getTransactionCreated());
    }

    void history() {
        for (int i = 0; i < transactions.size(); i++) {
            transaction = transactions.get(i);
            transactionInformation();
            history.add(data);
        }
    }

    private List<Transaction> getHistory(User user, LocalDateTime start, LocalDateTime end) {
        transactions = transactionRepository
                .findHistory(user, start, end);
        return transactions;
    }

    @Override
    public ResponseData<Object> addTransfer(Long senderId, Long receiverId, TransferDto request) throws Exception {
        Optional<User> senderIdOpt = userRepository.findById(senderId);
        Optional<User> receiverIdOpt = userRepository.findById(receiverId);

        userValidator.validateUserNotFound(senderIdOpt);
        userValidator.validateUserNotFound(receiverIdOpt);

        sender = senderIdOpt.get();
        receiver = receiverIdOpt.get();

        userValidator.validateBalance(sender.getBalance());

        transactionValidator.validateSenderAndReceiver(sender, receiver);

        transactionValidator.validateMinimumAmount(request.getAmount());

        // expense sender
        transaction = new Transaction(request.getAmount(), request.getNotes(), sender, receiver, "Transfer", true,
                LocalDateTime.now(), false);

        // income receiver
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
    public ResponseData<Object> getSumIncomeOrExpense(Long userId) throws Exception {
        Optional<User> userIdOpt = userRepository.findById(userId);

        userValidator.validateUserNotFound(userIdOpt);

        user = userIdOpt.get();

        sumExpense = 0;
        sumIncome = 0;

        transactions = transactionRepository.findSum(user);

        listData = new HashMap<>();

        for (int i = 0; i < transactions.size(); i++) {
            transaction = transactions.get(i);
            if (transaction.getTransactionCategory().equals(false)) {
                sumExpense += transaction.getAmount();
            } else if (transaction.getTransactionCategory().equals(true)) {
                sumIncome += transaction.getAmount();
            }
        }

        listData.put("sumIncome", sumIncome);
        listData.put("sumExpense", sumExpense);

        transactionValidator.validateNoTransactions(transactions);

        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Success", listData);
        return responseData;
    }

    // Find history today - 7 days ago
    @Override
    public ResponseData<Object> getThisWeekHistory(Long userId) throws Exception {
        Optional<User> userIdOpt = userRepository.findById(userId);

        userValidator.validateUserNotFound(userIdOpt);

        user = userIdOpt.get();

        transactions = getHistory(user, LocalDateTime.now().minusDays(7), LocalDateTime.now());

        history = new ArrayList<>();

        history();

        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Success", history);
        return responseData;
    }

    // Find history 7 days ago - this month
    @Override
    public ResponseData<Object> getThisMonthHistory(Long userId) throws Exception {
        Optional<User> userIdOpt = userRepository.findById(userId);

        userValidator.validateUserNotFound(userIdOpt);

        user = userIdOpt.get();

        transactions = getHistory(user, LocalDateTime.now().minusDays(37), LocalDateTime.now().minusDays(7));

        history = new ArrayList<>();

        history();

        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Success", history);
        return responseData;
    }

    // Find day in a week (chart)
    @Override
    public ResponseData<Object> getChart(Long userId) throws Exception {
        sumExpense = 0;
        sumIncome = 0;

        Optional<User> userIdOpt = userRepository.findById(userId);

        user = userIdOpt.get();

        // find income/expenses by day
        transactions = transactionRepository.findHistoryAsc(user);

        // transactions = getHistory(user, LocalDateTime.now().minusDays(7),
        // LocalDateTime.now());

        listData = new HashMap<>();
        history = new ArrayList<>();
        sorted = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            transaction = transactions.get(i);

            if (listData.isEmpty()) {
                if (transaction.getTransactionCategory().equals(false)) {
                    sumExpense += transaction.getAmount();
                } else if (transaction.getTransactionCategory().equals(true)) {
                    sumIncome += transaction.getAmount();
                }
                listData.put("Day",
                        transaction.getTransactionCreated().getDayOfWeek());
                listData.put("Income", sumIncome);
                listData.put("Expense", sumExpense);
                history.add(listData);
            } else if (listData.containsValue(transaction.getTransactionCreated().getDayOfWeek())) {
                if (transaction.getTransactionCategory().equals(false)) {
                    sumExpense += transaction.getAmount();
                } else if (transaction.getTransactionCategory().equals(true)) {
                    sumIncome += transaction.getAmount();
                }
                listData.put("Income", sumIncome);
                listData.put("Expense", sumExpense);
            } else {
                listData = new HashMap<>();
                sumExpense = 0;
                sumIncome = 0;
                if (transaction.getTransactionCategory().equals(false)) {
                    sumExpense += transaction.getAmount();
                } else if (transaction.getTransactionCategory().equals(true)) {
                    sumIncome += transaction.getAmount();
                }
                listData.put("Day",
                        transaction.getTransactionCreated().getDayOfWeek());
                listData.put("Income", sumIncome);
                listData.put("Expense", sumExpense);
                history.add(listData);
            }
        }

        List<Object> daysArrayName = new ArrayList<>() {
            {
                add(LocalDateTime.now().minusDays(6).getDayOfWeek());
                add(LocalDateTime.now().minusDays(5).getDayOfWeek());
                add(LocalDateTime.now().minusDays(4).getDayOfWeek());
                add(LocalDateTime.now().minusDays(3).getDayOfWeek());
                add(LocalDateTime.now().minusDays(2).getDayOfWeek());
                add(LocalDateTime.now().minusDays(1).getDayOfWeek());
                add(LocalDateTime.now().getDayOfWeek());
            }
        };

        sorted = new ArrayList<>(7);

        for (int i = 0; i < daysArrayName.size(); i++) {
            listData = new HashMap<>();
            sumExpense = 0;
            sumIncome = 0;
            listData.put("Day",
                    daysArrayName.get(i));
            listData.put("Income", sumIncome);
            listData.put("Expense", sumExpense);
            sorted.add(listData);

            for (int j = 0; j < history.size(); j++) {
                if (history.get(j).containsValue((daysArrayName.get(i)))) {
                    sorted.set(i, history.get(j));
                }
            }
        }

        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Success", sorted);

        return responseData;
    }

}