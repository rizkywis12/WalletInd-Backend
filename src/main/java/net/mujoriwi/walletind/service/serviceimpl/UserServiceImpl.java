package net.mujoriwi.walletind.service.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import net.mujoriwi.walletind.model.dto.request.ChangePasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.model.dto.request.ForgotPasswordDto;
import net.mujoriwi.walletind.model.dto.request.LoginDto;
import net.mujoriwi.walletind.model.dto.request.RegisterDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.model.entity.User;
import net.mujoriwi.walletind.repository.UserRepository;
import net.mujoriwi.walletind.service.service.UserService;
import net.mujoriwi.walletind.validator.UserValidator;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private ResponseData<Object> responseData;
    private User user;
    private List<User> users;

    @Autowired
    private UserValidator userValidator;

    private Map<Object, Object> data;

    void userInformation() {
        data = new HashMap<>();
        data.put("id", user.getId());
        data.put("email", user.getEmail());
        data.put("username", user.getUserName());
        data.put("balance", user.getBalance());
    }

    @Override
    public ResponseData<Object> register(RegisterDto request) throws Exception {
        Optional<User> emailOpt = userRepository.findByEmail(request.getEmail());
        userValidator.validateEmailExist(emailOpt);

        Optional<User> userNameOpt = userRepository.findByUserName(request.getUserName());
        userValidator.validateUserNameExist(userNameOpt);

        user = new User(request.getEmail(), request.getPassword());
        user.setUserName(request.getUserName());
        userRepository.save(user);

        userInformation();

        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Success register!", data);

        return responseData;
    }

    @Override
    public ResponseData<Object> login(LoginDto request) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        userValidator.validateUserNotFound(userOpt);

        user = userOpt.get();

        userValidator.validatePassword(request.getPassword(), user.getPassword());

        userInformation();

        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Success Login", data);

        return responseData;
    }

    @Override
    public ResponseData<Object> forgotPassword(ForgotPasswordDto request) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        userValidator.validateUserNotFound(userOpt);

        user = userOpt.get();

        userValidator.validateForgotPassword(user.getPassword(), request.getPassword());

        userValidator.validateConfirmPassword(request.getPassword(), request.getConfirmPassword());

        user.setPassword(request.getPassword());

        userInformation();

        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Successfully update your password!", data);

        return responseData;
    }

    @Override
    public ResponseData<Object> changePassword(long id, ChangePasswordDto request) throws Exception {
        Optional<User> userOpt = userRepository.findById(id);
        userValidator.validateUserNotFound(userOpt);
        user = userOpt.get();
        userValidator.validateCurrentPassword(user.getPassword(), request.getCurrentPassword());
        userValidator.validateConfirmPassword(request.getNewPassword(), request.getNewConfirmPassword());

        user.setPassword(request.getNewPassword());
        userInformation();

        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Successfully Change your password!", data);

        return responseData;
    }

    @Override
    public ResponseData<Object> getAll() {
        users = userRepository.findAll();

        // response data
        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "success", users);
        return responseData;
    }

    @Override
    public ResponseData<Object> getUserById(Long id) throws Exception {
       
        Optional<User> userOpt = userRepository.findById(id);
        userValidator.validateUserNotFound(userOpt);
        user = userOpt.get();
        userInformation();
        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "success", data);
        return responseData;
        
    }

}
