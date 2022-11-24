package net.mujoriwi.walletind.service.service;

import net.mujoriwi.walletind.model.dto.request.PinDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;

public interface PinService {
    ResponseData<Object> pin(long id,PinDto request) throws Exception;
}

