package net.mujoriwi.walletind.service.mail;

import net.mujoriwi.walletind.model.dto.request.MailDto;


public interface MailService {
    void sendMail(MailDto request);
}
