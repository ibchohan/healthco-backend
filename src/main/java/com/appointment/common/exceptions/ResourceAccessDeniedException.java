package com.appointment.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ResourceAccessDeniedException extends org.springframework.security.core.AuthenticationException {

    static final long serialVersionUID = -3297516993334110048L;

    public ResourceAccessDeniedException()
    {
        super("Access Denied!");
    }

    public ResourceAccessDeniedException(String message)
    {
        super(message);
    }

}