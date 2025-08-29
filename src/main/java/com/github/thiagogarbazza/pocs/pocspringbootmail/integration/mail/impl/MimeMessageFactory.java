package com.github.thiagogarbazza.pocs.pocspringbootmail.integration.mail.impl;

import com.github.thiagogarbazza.pocs.pocspringbootmail.integration.mail.Email;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
class MimeMessageFactory {

    private final JavaMailSender javaMailSender;

    public Collection<MimeMessage> create(final Collection<Email> emails) throws MessagingException, IOException {
        final Collection<MimeMessage> mimeMessages = new ArrayList<>();
        for (Email email : emails) {
            MimeMessage mimeMessage = create(email);
            mimeMessages.add(mimeMessage);
        }
        return mimeMessages;
    }

    private MimeMessage create(final Email email) throws MessagingException, IOException {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setPriority(email.getPriority().getCode());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setText(email.getBody(), true);
        mimeMessageHelper.setFrom(email.getFrom());
        mimeMessageHelper.setTo(email.getTos().toArray(new String[0]));
        mimeMessageHelper.setCc(email.getCcs().toArray(new String[0]));
        mimeMessageHelper.setBcc(email.getBccs().toArray(new String[0]));
        for (Email.Attachment attachment : email.getAttachments()) {
            final DataSource dataSource = new ByteArrayDataSource(attachment.getInputStream(), attachment.getMimetype());
            mimeMessageHelper.addAttachment(attachment.getFilename(), dataSource);
        }

        return mimeMessage;
    }
}
