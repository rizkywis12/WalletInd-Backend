package net.mujoriwi.walletind.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component("emailTemplate")
public class EmailTemplate {
    @Autowired
    JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
 ;
    //    jangan di delete donk wkwk
    public EmailTemplate(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    public String getForgotPasswordTemplate() {
        Context context = new Context();
        String htmlContent = this.templateEngine.process("templateEmail.html", context);
        return htmlContent;
    }

}


