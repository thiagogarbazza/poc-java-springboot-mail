package com.github.thiagogarbazza.pocs.pocspringbootmail.integration.mail;

import lombok.*;

import java.io.InputStream;
import java.util.Collection;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Email {

    @Builder.Default
    private final Priority priority = Priority.NORMAL;
    private final String from;
    @Singular
    private final Collection<String> tos;
    @Singular
    private final Collection<String> ccs;
    @Singular
    private final Collection<String> bccs;
    private final String subject;
    private final String body;
    @Singular
    private final Collection<Attachment> attachments;

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
    public enum Priority {

        HIGHEST(1),
        HIGH(2),
        NORMAL(3),
        LOW(4),
        LOWEST(5);

        private final int code;
    }

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Attachment {
        private final String filename;
        private final String mimetype;
        private final InputStream inputStream;

    }
}
