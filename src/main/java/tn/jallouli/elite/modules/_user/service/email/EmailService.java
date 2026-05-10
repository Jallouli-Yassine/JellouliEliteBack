package tn.jallouli.elite.modules._user.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailAsyncService emailAsyncService;
    private static final String FIRSTNAME = "firstName";


    public void sendWelcomeEmail(String to, String firstName) {

        emailAsyncService.sendEmail(
                to,
                "welcome-email",
                "Welcome to ArenaLink!",
                Map.of(FIRSTNAME, firstName)
        );
    }

    public void sendPasswordResetEmail(String to, String firstName, String resetUrl) {

        emailAsyncService.sendEmail(
                to,
                "password-reset",
                "Reset Your Password",
                Map.of(FIRSTNAME, firstName, "resetUrl", resetUrl)
        );
    }

    public void sendPasswordUpdateSuccessEmail(String to, String firstName) {
        emailAsyncService.sendEmail(to, "password-updated", "Your Password Has Been Updated Successfully",
                Map.of(FIRSTNAME, firstName));
    }
}