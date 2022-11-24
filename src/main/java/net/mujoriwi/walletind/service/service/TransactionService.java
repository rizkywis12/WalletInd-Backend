package net.mujoriwi.walletind.service.service;

import net.mujoriwi.walletind.model.dto.request.TransferDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;

public interface TransactionService {
    ResponseData<Object> addTransfer(Long senderId, Long receiverId, TransferDto request) throws Exception;

    ResponseData<Object> getTransferCategory(Long userId, Boolean status) throws Exception;

}
