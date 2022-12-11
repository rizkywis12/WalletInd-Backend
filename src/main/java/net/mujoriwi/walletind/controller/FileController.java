package net.mujoriwi.walletind.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.model.entity.FileEntity;
import net.mujoriwi.walletind.service.service.FileService;

@RestController
@RequestMapping("/files")
@CrossOrigin(value = "http://localhost:3000")
public class FileController {
  @Autowired
  private FileService fileService;

  private ResponseData<Object> responseData;

   @PostMapping("/upload/{user_id}")
  public ResponseEntity<Object> uploadFile(@Valid @RequestParam("file") MultipartFile file, @PathVariable Long user_id)
      throws IOException, Exception {
    long size = file.getSize();
    if (size > 2097152) // sama dengan 2MB
    {
      responseData = new ResponseData<Object>(400, "Image cannot more than 2mb", null);
      return ResponseEntity.badRequest().body(responseData);
    } else {
      fileService.store(file, user_id);
      String message = "Uploaded the file successfully: " + file.getOriginalFilename();
      responseData = new ResponseData<Object>(200, message, null);
      return ResponseEntity.ok().body(responseData);
    }
  }

  @PutMapping("/upload/{user_id}")
  public ResponseEntity<Object> updateFile(@RequestParam("file") MultipartFile file, @PathVariable Long user_id)
      throws IOException, Exception {
    long size = file.getSize();
    if (size > 2097152) // sama dengan 2MB
    {
      responseData = new ResponseData<Object>(400, "Image cannot more than 2mb", null);
      return ResponseEntity.badRequest().body(responseData);
    } else {
      fileService.updateFile(file, user_id);
      String message = "Updateded the file successfully: " + file.getOriginalFilename();
      responseData = new ResponseData<Object>(200, message, null);
      return ResponseEntity.ok().body(responseData);
    }
  }

  @GetMapping("/{user_id}")
  public ResponseEntity<Object> getFile(@PathVariable Long user_id) throws Exception {
    FileEntity fileEntity = fileService.getFile(user_id);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
        .body(fileEntity.getData());
  }
}
