package net.mujoriwi.walletind.service.mail;

import net.mujoriwi.walletind.model.dto.request.MailDto;
import net.mujoriwi.walletind.model.entity.User;

import javax.mail.MessagingException;


public interface MailService {
    void sendMail
            (User user) throws MessagingException;
}
