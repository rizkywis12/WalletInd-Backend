package net.mujoriwi.walletind.service.mail;

import net.mujoriwi.walletind.model.dto.request.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendMail(MailDto request) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(request.getRecipient());
        message.setSubject(request.getSubject());
        message.setText(request.getMessage());

        javaMailSender.send(message);
    }
}
