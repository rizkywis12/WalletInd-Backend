package net.mujoriwi.walletind.controller;


import net.mujoriwi.walletind.model.dto.request.PinDto;
import net.mujoriwi.walletind.model.dto.request.RegisterDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.service.service.PinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class PinController {

    @Autowired
    private PinService pinService;
    private ResponseData<Object> responseData;
    @PostMapping("/pin/{id}")
    public ResponseEntity<Object> pin (@PathVariable long id,@RequestBody @Valid PinDto request) throws Exception {
        responseData = pinService.pin(id,request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
}
