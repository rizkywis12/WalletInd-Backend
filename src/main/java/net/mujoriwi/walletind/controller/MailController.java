package net.mujoriwi.walletind.controller;

import net.mujoriwi.walletind.model.entity.User;
import net.mujoriwi.walletind.service.mail.MailService;
import net.mujoriwi.walletind.model.dto.request.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping
    public String sendMail(@RequestBody User user)throws MessagingException {
        mailService.sendMail(user);
        return "Email Sent Successfully.!";
    }
}