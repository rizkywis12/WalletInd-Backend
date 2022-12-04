package net.mujoriwi.walletind.service.service;

import net.mujoriwi.walletind.model.dto.request.TopUpDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;

public interface TopUpService {
    ResponseData<Object> addTopUp(TopUpDto request) throws Exception;

    ResponseData<Object> getAllTopUp() throws Exception;

    ResponseData<Object> getTopUpById(long id) throws Exception;
}