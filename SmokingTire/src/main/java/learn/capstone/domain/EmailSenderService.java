package learn.capstone.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender sender;

    public void sendEmail(String toEmail, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("smokingtiresjavabros@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject("Order Confirmation");

        sender.send(message);
    }

}
