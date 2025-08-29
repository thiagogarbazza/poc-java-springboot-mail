package com.github.thiagogarbazza.pocs.pocspringbootmail.integration.mail;

import java.util.Collection;

public interface EmailService {

    void send(Email email);

    void send(Collection<Email> emails);
}
