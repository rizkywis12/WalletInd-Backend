package net.mujoriwi.walletind.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.mujoriwi.walletind.model.dto.request.TopUpDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.service.service.TopUpService;

@RestController
@RequestMapping("/payment-name")
@CrossOrigin(value = "http://localhost:3000")
public class TopUpController {
    @Autowired
    private TopUpService topUpService;

    private ResponseData<Object> responseData;

    @PostMapping("/add-payment")
    public ResponseEntity<Object> topup(@RequestBody @Valid TopUpDto request) throws Exception {
        responseData = topUpService.addTopUp(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
}
