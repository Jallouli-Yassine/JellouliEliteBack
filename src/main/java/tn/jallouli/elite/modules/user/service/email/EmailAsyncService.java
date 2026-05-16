package tn.jallouli.elite.modules.user.service.email;


import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailAsyncService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${app.mail.from}")
    private String fromEmail;

    private static final Logger logger =
            LoggerFactory.getLogger(EmailAsyncService.class);

    @Async
    public void sendEmail(String to,
                          String templateName,
                          String subject,
                          Map<String, Object> vars) {

        try {

            Context context = new Context();
            context.setVariables(vars);

            String content =
                    templateEngine.process(templateName, context);

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(fromEmail);
            helper.setText(content, true);

            mailSender.send(message);

            logger.info("Email sent to {}", to);

        } catch (Exception e) {
            logger.error("Email error", e);
        }
    }
}