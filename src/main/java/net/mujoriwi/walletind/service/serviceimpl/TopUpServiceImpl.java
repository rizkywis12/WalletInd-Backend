package net.mujoriwi.walletind.service.serviceimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.model.dto.request.TopUpDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.model.entity.TopUp;
import net.mujoriwi.walletind.repository.TopUpRepository;
import net.mujoriwi.walletind.service.service.TopUpService;
import net.mujoriwi.walletind.validator.TopUpValidator;

@Service
@Transactional
public class TopUpServiceImpl implements TopUpService {
    @Autowired
    private TopUpRepository topUpRepository;

    private ResponseData<Object> responseData;
    private TopUp topUp;

    @Autowired
    private TopUpValidator topUpValidator;

   

    private Map<Object, Object> data;

    void topUpInformation() {
        data = new HashMap<>();
        data.put("payment name", topUp.getPaymentName());
    }

    @Override
    public ResponseData<Object> addTopUp(TopUpDto request) throws Exception {
        // TODO Auto-generated method stub
        Optional<TopUp> topUpOpt = topUpRepository.findByPaymentName(request.getPaymentName());
        topUpValidator.validatePaymentNameExist(topUpOpt);
        topUp = new TopUp(request.getPaymentName());
        
        topUpRepository.save(topUp);

        topUpInformation();
        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Success added payment!", data);

        return responseData;
    }
}
