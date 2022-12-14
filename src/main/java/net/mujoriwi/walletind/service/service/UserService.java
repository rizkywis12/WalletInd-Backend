package net.mujoriwi.walletind.service.service;

import net.mujoriwi.walletind.model.dto.request.*;
import net.mujoriwi.walletind.model.dto.response.ResponseData;

public interface UserService {
    ResponseData<Object> register(RegisterDto request) throws Exception;

    ResponseData<Object> login(LoginDto request) throws Exception;

    ResponseData<Object> forgotPassword(String Email,ForgotPasswordDto request) throws Exception;

    ResponseData<Object> changePassword(long id, ChangePasswordDto request) throws Exception;

    ResponseData<Object> getAll(long id);

    ResponseData<Object>  verficationEmail(EmailRequest email) throws Exception;
    ResponseData<Object> getBalance(long id) throws Exception;
}
