package net.mujoriwi.walletind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.mujoriwi.walletind.model.dto.request.DetailUserDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.service.service.DetailUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "https://walletind.vercel.app/")
public class DetailUserController {
    @Autowired
    private DetailUserService detailUserService;

    private ResponseData<Object> responseData;

    @PostMapping("/detail/{id}")
    public ResponseEntity<Object> postDetailUser(@PathVariable long id, @Valid @RequestBody DetailUserDto request)
            throws Exception {
        responseData = detailUserService.addDetailUser(id, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);

    }

    @PutMapping("/detail/update/{id}")
    public ResponseEntity<Object> updateDetailUser(@PathVariable long id, @Valid @RequestBody DetailUserDto request)
            throws Exception {
        responseData = detailUserService.updateDetailUser(id, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);

    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) throws Exception {
        responseData = detailUserService.getDetailUserById(id);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @GetMapping("/detail/getall/{id}")
    public ResponseEntity<Object> getAllUser(@PathVariable long id) throws Exception {
        responseData = detailUserService.getAllUserExceptCurrentUser(id);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @GetMapping("/detail/receiver/{id}")
    public ResponseEntity<Object> getReceiverById(@PathVariable long id) throws Exception {
        responseData = detailUserService.getUserInformationById(id);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
}
