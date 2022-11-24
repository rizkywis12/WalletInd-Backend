package net.mujoriwi.walletind.service.serviceimpl;


import net.mujoriwi.walletind.model.dto.request.PinDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.model.entity.Pin;
import net.mujoriwi.walletind.repository.PinRepository;
import net.mujoriwi.walletind.service.service.PinService;
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

    private ResponseData<Object> responseData;

    private Pin pin;

    private Map<Object, Object> data;


    void PinInformation() {
        data = new HashMap<>();
        data.put("Pin", pin.getPin());
    }


    @Override
    public ResponseData<Object> pin(PinDto request) throws Exception {
        Optional<Pin> pinOpt = pinRepository.findByUserId(request.getPin());

        pin = new Pin(request.getPin());
        pin.setPin(request.getPin());
        pinRepository.save(pin);

        PinInformation();

        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Create Pin Succes", data);

        return responseData;

    }

}
