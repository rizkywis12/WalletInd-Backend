package net.mujoriwi.walletind.service.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import net.mujoriwi.walletind.model.dto.request.TopUpDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    private List<Map<Object, Object>> listTopUp;

    void topUpInformation() {
        data = new HashMap<>();
        data.put("paymentId", topUp.getId());
        data.put("paymentName", topUp.getPaymentName());
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/top-up/")
                .path(Long.toString(topUp.getId()))
                .toUriString();
        data.put("paymentImage", fileDownloadUri);
    }

    @Override
    public ResponseData<Object> addTopUp(TopUpDto request) throws Exception {
        Optional<TopUp> topUpOpt = topUpRepository.findByPaymentName(request.getPaymentName());
        topUpValidator.validatePaymentNameExist(topUpOpt);
        topUp = new TopUp(request.getPaymentName());

        topUpRepository.save(topUp);

        topUpInformation();

        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Success added payment!", data);

        return responseData;
    }

    @Override
    public ResponseData<Object> getAllTopUp() throws Exception {
        List<TopUp> topUps = topUpRepository.findAll();

        topUpValidator.validateListTopUpNotFound(topUps);

        listTopUp = new ArrayList<>();

        for (int i = 0; i < topUps.size(); i++) {
            topUp = topUps.get(i);
            topUpInformation();
            listTopUp.add(data);
        }

        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Success get list topup!", listTopUp);

        return responseData;
    }

    @Override
    public ResponseData<Object> getTopUpById(long id) throws Exception {
        Optional<TopUp> topUpOpt = topUpRepository.findById(id);

        topUpValidator.validateTopUpNotFound(topUpOpt);

        topUp = topUpOpt.get();

        topUpInformation();

        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Success get data payment!", data);

        return responseData;
    }
}
