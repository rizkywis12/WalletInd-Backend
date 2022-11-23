package net.mujoriwi.walletind.service.serviceimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.model.dto.request.DetailUserDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.model.entity.DetailUser;
import net.mujoriwi.walletind.model.entity.User;
import net.mujoriwi.walletind.repository.DetailUserRepository;
import net.mujoriwi.walletind.repository.UserRepository;
import net.mujoriwi.walletind.service.service.DetailUserService;
import net.mujoriwi.walletind.validator.DetailUserValidator;
import net.mujoriwi.walletind.validator.UserValidator;


@Service
@Transactional
public class DetailUserServiceImpl implements DetailUserService {

    @Autowired
    private UserRepository userRepository;

    

    private ResponseData<Object> responseData;
    private User user;

    @Autowired
    private UserValidator userValidator;
    @Autowired
    private DetailUserValidator DetailUserValidator;

    @Autowired 
    private DetailUserRepository detailUserRepository;

    private DetailUser detailUser;

    private Map<Object, Object> data;

    void DetailuserInformation() {
        data = new HashMap<>();
        data.put("First Name", detailUser.getFirstName());
        data.put("Last Name", detailUser.getLastName());
        data.put("Phone Number", detailUser.getPhoneNumber());

    }

    @Override
    public ResponseData<Object> addDetailUser(Long id , DetailUserDto request) throws Exception {
        Optional<User> userOpt = userRepository.findById(id);
        
        userValidator.validateUserNotFound(userOpt);
        user = userOpt.get();
        Optional<DetailUser> detailUserOpt = detailUserRepository.findByUserId(user);
        DetailUserValidator.validateUserIdExist(detailUserOpt);
        detailUser = new DetailUser(request.getFirstName(), request.getLastName(), request.getPhoneNumber());
        detailUser.setUserId(user);
        detailUserRepository.save(detailUser);
        DetailuserInformation();
        // TODO Auto-generated method stub
        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Succes add information", data);

        return responseData; 
    }

    @Override
    public ResponseData<Object> updateDetailUser(Long id, DetailUserDto request) {
        // TODO Auto-generated method stub
        return null;
    }
    
}