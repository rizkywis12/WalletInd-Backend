package net.mujoriwi.walletind.service.serviceimpl;


import net.mujoriwi.walletind.model.dto.request.PinDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.model.entity.DetailUser;
import net.mujoriwi.walletind.model.entity.Pin;
import net.mujoriwi.walletind.model.entity.User;
import net.mujoriwi.walletind.repository.PinRepository;
import net.mujoriwi.walletind.repository.UserRepository;
import net.mujoriwi.walletind.service.service.PinService;
import net.mujoriwi.walletind.validator.PinValidator;
import net.mujoriwi.walletind.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class PinServiceImpl implements PinService {

    @Autowired
    private PinRepository pinRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private PinValidator pinValidator;

    private ResponseData<Object> responseData;
    private User user;
    private Pin pin;

    private Map<Object, Object> data;


    void PinInformation() {
        data = new HashMap<>();
        data.put("ID", pin.getId());
        data.put("Pin", pin.getPin());
    }


    @Override
    public ResponseData<Object> addpin(long id, PinDto request) throws Exception {
        Optional<User> userOpt = userRepository.findById(id);

        userValidator.validateUserNotFound(userOpt);
        user = userOpt.get();
        Optional<Pin> pinOpt = pinRepository.findByUserId(user);
        pinValidator.validatePinExist(pinOpt);
        pin = new Pin(request.getPin());
        pin.setUserId(user);
        pin.setPin(request.getPin());
        pinRepository.save(pin);
        PinInformation();

        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Create Pin Succes", data);

        return responseData;
    }
    @Override
    public ResponseData<Object> updatePin(long id, PinDto request) throws Exception {
        Optional<User> userOpt = userRepository.findById(id);
        userValidator.validateUserNotFound(userOpt);
        user = userOpt.get();
        Optional<Pin> pinOptional = pinRepository.findByUserId(user);
        pin = pinOptional.get();
        user = userOpt.get();
        pin.setUserId(user);
        pin.setPin(request.getPin());
        pinRepository.save(pin);
        PinInformation();

        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Create Pin Succes", data);

        return responseData;
    }
}
