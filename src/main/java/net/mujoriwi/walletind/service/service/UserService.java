package net.mujoriwi.walletind.service.service;

import net.mujoriwi.walletind.model.dto.request.ChangePasswordDto;
import net.mujoriwi.walletind.model.dto.request.ForgotPasswordDto;
import net.mujoriwi.walletind.model.dto.request.LoginDto;
import net.mujoriwi.walletind.model.dto.request.RegisterDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;

public interface UserService {
    ResponseData<Object> register(RegisterDto request) throws Exception;

    ResponseData<Object> login(LoginDto request) throws Exception;

    ResponseData<Object> forgotPassword(ForgotPasswordDto request) throws Exception;

    ResponseData<Object> changePassword(long id, ChangePasswordDto request) throws Exception;

<<<<<<< HEAD
    ResponseData<Object> getUserById(Long id) throws Exception;

    ResponseData<Object> getAll();
=======
    ResponseData<Object> getAll(long id);
>>>>>>> 4181846de1667d79b60981ee862fdcbc70437284

    ResponseData<Object> getBalance(long id) throws Exception;
}
