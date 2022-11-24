package net.mujoriwi.walletind.service.service;

import net.mujoriwi.walletind.model.dto.request.LoginDto;
import net.mujoriwi.walletind.model.dto.request.RegisterDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;

public interface UserService {
    ResponseData<Object> register(RegisterDto request) throws Exception;

    ResponseData<Object> login(LoginDto request) throws Exception;

}
