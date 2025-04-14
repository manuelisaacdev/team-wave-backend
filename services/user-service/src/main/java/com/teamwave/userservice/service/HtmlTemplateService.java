package com.teamwave.userservice.service;

import com.teamwave.userservice.exception.HtmlTemplateServiceException;
import com.teamwave.userservice.model.User;

public interface HtmlTemplateService {
    String recovery(User user, String recoveryToken) throws HtmlTemplateServiceException;
    String updateEmail(User user, String emailToken) throws HtmlTemplateServiceException;
    String accountActivation(User user, String emailToken) throws HtmlTemplateServiceException;
}
