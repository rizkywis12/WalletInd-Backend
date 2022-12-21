package net.mujoriwi.walletind.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.mujoriwi.walletind.model.dto.request.TopUpTransactionDto;
import net.mujoriwi.walletind.model.dto.request.TransferDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.service.service.TransactionService;

@RestController
@RequestMapping("/transactions")

public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    private ResponseData<Object> responseData;

    // Create transfer
    @PostMapping("/transfer/{senderId}/{receiverId}")
    public ResponseEntity<Object> transfer(@PathVariable long senderId, @PathVariable long receiverId,
            @RequestBody @Valid TransferDto request) throws Exception {
        responseData = transactionService.addTransfer(senderId, receiverId, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @PostMapping("/topup/{topUpId}/{receiverId}")
    public ResponseEntity<Object> topUp(@PathVariable long topUpId, @PathVariable long receiverId,
            @RequestBody @Valid TopUpTransactionDto request) throws Exception {
        responseData = transactionService.addTopUp(topUpId, receiverId, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    // Find transaction income/expenses or all transaction by user (notification and
    // transaction history)
    @GetMapping("/transfer/{userId}")
    public ResponseEntity<Object> getSumIncomeOrExpense(@PathVariable long userId) throws Exception {
        responseData = transactionService.getSumIncomeOrExpense(userId);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @GetMapping("/transfer/week/{userId}")
    public ResponseEntity<Object> getThisWeekHistory(@PathVariable long userId)
            throws Exception {
        responseData = transactionService.getThisWeekHistory(userId);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @GetMapping("/transfer/month/{userId}")
    public ResponseEntity<Object> getThisMonthHistory(@PathVariable long userId)
            throws Exception {
        responseData = transactionService.getThisMonthHistory(userId);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @GetMapping("/transfer/chart/{userId}")
    public ResponseEntity<Object> getChartHistory(@PathVariable long userId)
            throws Exception {
        responseData = transactionService.getChart(userId);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

}
