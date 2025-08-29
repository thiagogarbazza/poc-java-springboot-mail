package com.github.thiagogarbazza.pocs.pocspringbootmail.integration.mail.impl;

import com.github.thiagogarbazza.pocs.pocspringbootmail.integration.mail.Email;
import com.github.thiagogarbazza.pocs.pocspringbootmail.integration.mail.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final MimeMessageFactory mimeMessageFactory;

    @Override
    public void send(Email email) {
        send(Collections.singleton(email));
    }

    @Override
    public void send(Collection<Email> emails) {
        try {
            final Collection<MimeMessage> mimeMessages = mimeMessageFactory.create(emails);
            javaMailSender.send(mimeMessages.toArray(new MimeMessage[0]));
        } catch (MessagingException | IOException  e) {
            throw new RuntimeException(e);
        }
    }
}
