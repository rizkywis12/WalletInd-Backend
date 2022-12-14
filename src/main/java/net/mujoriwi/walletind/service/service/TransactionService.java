package net.mujoriwi.walletind.service.service;

import net.mujoriwi.walletind.model.dto.request.TopUpTransactionDto;
import net.mujoriwi.walletind.model.dto.request.TransferDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;

public interface TransactionService {
    ResponseData<Object> addTransfer(Long senderId, Long receiverId, TransferDto request) throws Exception;

    ResponseData<Object> addTopUp(Long topUpid, Long receiverId, TopUpTransactionDto request) throws Exception;

    ResponseData<Object> getSumIncomeOrExpense(Long userId) throws Exception;

    ResponseData<Object> getThisWeekHistory(Long userId) throws Exception;

    ResponseData<Object> getThisMonthHistory(Long userId) throws Exception;

    ResponseData<Object> getChart(Long userId) throws Exception;
}
