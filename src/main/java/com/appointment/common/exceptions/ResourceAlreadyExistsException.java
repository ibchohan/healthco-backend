package com.appointment.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends Exception {

    static final long serialVersionUID = -3387516543334229948L;

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

}