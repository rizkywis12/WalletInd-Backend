package net.mujoriwi.walletind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.mujoriwi.walletind.model.dto.request.TransferDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.service.service.TransactionService;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(value = "http://localhost:3000")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    private ResponseData<Object> responseData;

    // Create transfer
    @PostMapping("/transfer/{senderId}/{receiverId}")
    public ResponseEntity<Object> transfer(@PathVariable long senderId, @PathVariable long receiverId,
            @RequestBody TransferDto request) throws Exception {
        responseData = transactionService.addTransfer(senderId, receiverId, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    // Find transfer income or transfer expense or all transfer by current user
    @GetMapping("/transfer/{userId}")
    public ResponseEntity<Object> transfer(@PathVariable long userId,
            @RequestParam(value = "status", defaultValue = "") Boolean status) throws Exception {
        responseData = transactionService.getTransferCategory(userId, status);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

}
