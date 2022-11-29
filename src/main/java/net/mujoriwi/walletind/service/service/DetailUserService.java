package net.mujoriwi.walletind.service.service;

import net.mujoriwi.walletind.model.dto.request.DetailUserDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;

public interface DetailUserService {
    ResponseData<Object> addDetailUser(Long id, DetailUserDto request) throws Exception;

    ResponseData<Object> updateDetailUser(Long id, DetailUserDto request) throws Exception;

    ResponseData<Object> getDetailUserById(Long id) throws Exception;

    ResponseData<Object> getAllUserExceptCurrentUser(Long id) throws Exception;

    ResponseData<Object> getUserInformationById(Long id) throws Exception;

}
