package net.mujoriwi.walletind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.mujoriwi.walletind.model.dto.request.DetailUserDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.service.service.DetailUserService;

@RestController
@RequestMapping
@CrossOrigin(value = "http://localhost:3000")
public class DetailUserController {
    @Autowired
    private DetailUserService detailUserService;

    private ResponseData<Object> responseData;

    @PostMapping("/detail-user/{id}")
    public ResponseEntity<Object> postDetailUser(@PathVariable long id , @RequestBody DetailUserDto request) throws Exception {
        responseData = detailUserService.addDetailUser(id, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);

    }
}
