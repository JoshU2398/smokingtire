package learn.capstone.controllers;

import learn.capstone.domain.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailSenderService service;

    @PostMapping("/{toEmail}/{body}")
    public void sendEmail(@PathVariable String toEmail, @PathVariable String body) {
        service.sendEmail(toEmail, body);
    }

}
