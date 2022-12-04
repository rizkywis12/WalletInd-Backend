package net.mujoriwi.walletind.controller;

import javax.validation.Valid;

import net.mujoriwi.walletind.model.dto.request.ChangePasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.mujoriwi.walletind.model.dto.request.ForgotPasswordDto;
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
    @PutMapping("/forgot-password/{email}")
    public ResponseEntity<Object> forgotPassword(@PathVariable String email ,@RequestBody @Valid ForgotPasswordDto request) throws Exception {
        responseData = userService.forgotPassword(email,request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<Object> changePassword(@PathVariable long id, @RequestBody @Valid ChangePasswordDto request)
            throws Exception {
        responseData = userService.changePassword(id, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @GetMapping("/getall/{id}")
    public ResponseEntity<Object> getAllUser(@PathVariable long id) {
        responseData = userService.getAll(id);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @GetMapping("/getbalance/{id}")
    public ResponseEntity<Object> getBalance(@PathVariable long id) throws Exception {
        responseData = userService.getBalance(id);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

}