package net.mujoriwi.walletind.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
    private String recipient;
    private String subject;
    private String message;
    private String properties;
}
