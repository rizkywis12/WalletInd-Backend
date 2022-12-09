package net.mujoriwi.walletind.service.service;

import net.mujoriwi.walletind.model.dto.request.PinDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;

public interface PinService {
    ResponseData<Object> addpin(long id,PinDto request) throws Exception;
    ResponseData<Object> updatePin(long id,PinDto request) throws Exception;
}

