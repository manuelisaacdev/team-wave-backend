package com.teamwave.userservice.service.impl;

import com.teamwave.userservice.exception.HtmlTemplateServiceException;
import com.teamwave.userservice.model.User;
import com.teamwave.userservice.service.HtmlTemplateService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Objects;

@Service
public class HtmlTemplateServiceImpl implements HtmlTemplateService {
    @Override
    public String recovery(User user, String recoveryToken) throws HtmlTemplateServiceException {
        try {
            return MessageFormat.format(
                Files.readString(Path.of(Objects.requireNonNull(getClass().getResource("/templates/recovery.html")).toURI())),
                user.getName(),
                recoveryToken
            );
        } catch (IOException | URISyntaxException e) {
            throw new HtmlTemplateServiceException(e);
        }
    }

    @Override
    public String updateEmail(User user, String emailToken) throws HtmlTemplateServiceException {
        try {
            return MessageFormat.format(
                    Files.readString(Path.of(Objects.requireNonNull(getClass().getResource("/templates/email-update.html")).toURI())),
                    user.getName(),
                    emailToken
            );
        } catch (IOException | URISyntaxException e) {
            throw new HtmlTemplateServiceException(e);
        }
    }

    @Override
    public String accountActivation(User user, String emailToken) throws HtmlTemplateServiceException {
        try {
            return MessageFormat.format(
                    Files.readString(Path.of(Objects.requireNonNull(getClass().getResource("/templates/account-activation.html")).toURI())),
                    user.getName(),
                    emailToken
            );
        } catch (IOException | URISyntaxException e) {
            throw new HtmlTemplateServiceException(e);
        }
    }
}
