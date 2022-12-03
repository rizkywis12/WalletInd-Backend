package net.mujoriwi.walletind.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.model.entity.FileTopUp;
import net.mujoriwi.walletind.service.service.FileTopUpService;

@RestController
@RequestMapping("/files/top-up")
public class FileTopUpController {

    @Autowired
    private FileTopUpService fileTopUpService;

    private ResponseData<Object> responseData;

    @PostMapping("/upload/{topUp_id}")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long topUp_id)
            throws IOException, Exception {

        fileTopUpService.store(file, topUp_id);

        String message = "Uploaded the file successfully: " + file.getOriginalFilename();

        responseData = new ResponseData<Object>(200, message, null);

        return ResponseEntity.ok().body(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFile(@PathVariable Long id) throws Exception {
        FileTopUp fileTopUp = fileTopUpService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileTopUp.getName() + "\"")
                .body(fileTopUp.getData());
    }

    @PutMapping("/update/{topUp_id}")
    public ResponseEntity<Object> changeFile(@RequestParam("file") MultipartFile file, @PathVariable Long topUp_id)
            throws IOException, Exception {

        fileTopUpService.updateFile(file, topUp_id);

        String message = "Change successfully added: " + file.getOriginalFilename();

        responseData = new ResponseData<Object>(200, message, null);

        return ResponseEntity.ok().body(responseData);
    }
}
