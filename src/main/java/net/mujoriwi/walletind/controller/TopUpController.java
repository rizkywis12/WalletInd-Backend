package net.mujoriwi.walletind.controller;

import javax.validation.Valid;

import net.mujoriwi.walletind.model.dto.request.TopUpDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.service.service.TopUpService;

@RestController
@RequestMapping("/top-up")
@CrossOrigin(value = "http://localhost:3000")
public class TopUpController {
    @Autowired
    private TopUpService topUpService;

    private ResponseData<Object> responseData;

    @PostMapping("/add-payment-name")
    public ResponseEntity<Object> topup(@RequestBody @Valid TopUpDto request) throws Exception {
        responseData = topUpService.addTopUp(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @GetMapping("/get-list-payment")
    public ResponseEntity<Object> getTopUp() throws Exception {
        responseData = topUpService.getAllTopUp();
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @GetMapping("/get-payment/{id}")
    public ResponseEntity<Object> getTopUp(@PathVariable long id) throws Exception {
        responseData = topUpService.getTopUpById(id);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
}
