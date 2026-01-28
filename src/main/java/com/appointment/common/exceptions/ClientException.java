package com.appointment.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ClientException extends Exception
{
    static final long serialVersionUID = -3387517893334119948L;


    public ClientException(String message)
    {
        super(message);
    }

}