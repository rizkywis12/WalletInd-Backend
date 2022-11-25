package net.mujoriwi.walletind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.mujoriwi.walletind.model.dto.request.DetailUserDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.service.service.DetailUserService;

@RestController
@RequestMapping("/detail-users")
@CrossOrigin(value = "http://localhost:3000")
public class DetailUserController {
    @Autowired
    private DetailUserService detailUserService;

    private ResponseData<Object> responseData;

    @PostMapping("/detail/{id}")
    public ResponseEntity<Object> postDetailUser(@PathVariable long id, @RequestBody DetailUserDto request)
            throws Exception {
        responseData = detailUserService.addDetailUser(id, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);

    }

    @PutMapping("/detail/update/{id}")
    public ResponseEntity<Object> updateDetailUser(@PathVariable long id, @RequestBody DetailUserDto request)
            throws Exception {
        responseData = detailUserService.updateDetailUser(id, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);

    }
}
