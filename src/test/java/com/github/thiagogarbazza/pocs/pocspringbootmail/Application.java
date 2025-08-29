package com.github.thiagogarbazza.pocs.pocspringbootmail;

import com.github.thiagogarbazza.pocs.pocspringbootmail.integration.mail.Email;
import com.github.thiagogarbazza.pocs.pocspringbootmail.integration.mail.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

    private final EmailService emailService;

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(final String... args) {
        sendEmail();
        sendEmailWithHtml();
        sendEmailWithMultipleTo();
        sendEmailWithMultipleCc();
        sendEmailWithMultipleBcc();
        sendEmailsWithAttachments();
        sendEmailsWithPriority();
    }

    private void sendEmailsWithAttachments() {
        emailService.send(Email.builder()
                .from("test@my-server.com")
                .to("test-attachments@my-server.com")
                .subject("Test e-mail with attachments")
                .body("This is a test e-mail.")
                .attachment(Email.Attachment.builder()
                        .filename("example.json")
                        .mimetype("application/json")
                        .inputStream(getClass().getResourceAsStream("/attachments/example.json"))
                        .build())
                .attachment(Email.Attachment.builder()
                        .filename("example.txt")
                        .mimetype("text/plain")
                        .inputStream(getClass().getResourceAsStream("/attachments/example.txt"))
                        .build())
                .attachment(Email.Attachment.builder()
                        .filename("example.yml")
                        .mimetype("application/yaml")
                        .inputStream(getClass().getResourceAsStream("/attachments/example.yml"))
                        .build())
                .build());
    }

    private void sendEmail() {
        emailService.send(Email.builder()
                .from("test@my-server.com")
                .to("test-simple@my-server.com")
                .subject("Test e-mail")
                .body("This is a test e-mail.")
                .build());
    }

    @SneakyThrows
    private void sendEmailWithHtml() {
        final InputStream inputStream = getClass().getResourceAsStream("/example-email.html");
        emailService.send(Email.builder()
                .from("test@my-server.com")
                .to("test-html@my-server.com")
                .subject("Test e-mail with html")
                .body(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8))
                .build());
    }

    private void sendEmailWithMultipleTo() {
        emailService.send(Email.builder()
                .from("test@my-server.com")
                .to("test-multiple-to-01@my-server.com")
                .to("test-multiple-to-02@my-server.com")
                .to("test-multiple-to-03@my-server.com")
                .to("test-multiple-to-04@my-server.com")
                .subject("Test e-mail with multiple to")
                .body("This is a test e-mail.")
                .build());
    }

    private void sendEmailWithMultipleCc() {
        emailService.send(Email.builder()
                .from("test@my-server.com")
                .cc("test-multiple-cc-01@my-server.com")
                .cc("test-multiple-cc-02@my-server.com")
                .cc("test-multiple-cc-03@my-server.com")
                .cc("test-multiple-cc-04@my-server.com")
                .subject("Test e-mail with multiple cc")
                .body("This is a test e-mail.")
                .build());
    }

    private void sendEmailWithMultipleBcc() {
        emailService.send(Email.builder()
                .from("test@my-server.com")
                .bcc("test-multiple-bcc-01@my-server.com")
                .bcc("test-multiple-bcc-02@my-server.com")
                .bcc("test-multiple-bcc-03@my-server.com")
                .bcc("test-multiple-bcc-04@my-server.com")
                .subject("Test e-mail with multiple bcc")
                .body("This is a test e-mail.")
                .build());
    }

    private void sendEmailsWithPriority() {
        final Collection<Email> emails = Arrays.stream(Email.Priority.values()).map(priority -> {
                    final String text = priority.getCode() + " - " + priority.name().toLowerCase();

                    return Email.builder()
                            .from("test@my-server.com")
                            .to("test-priority-" + priority.name().toLowerCase() + "@my-server.com")
                            .priority(priority)
                            .subject("Test e-mail priority " + text)
                            .body("This is a test e-mail priority " + text)
                            .build();
                })
                .collect(Collectors.toList());

        emailService.send(emails);
    }
}
