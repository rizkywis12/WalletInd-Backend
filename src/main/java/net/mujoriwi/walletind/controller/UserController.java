package net.mujoriwi.walletind.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.mujoriwi.walletind.model.dto.request.LoginDto;
import net.mujoriwi.walletind.model.dto.request.RegisterDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.service.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    private ResponseData<Object> responseData;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDto request) throws Exception {
        responseData = userService.register(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDto request) throws Exception {
        responseData = userService.login(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
}
