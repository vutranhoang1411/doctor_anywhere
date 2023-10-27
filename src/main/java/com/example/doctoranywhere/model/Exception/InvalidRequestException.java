package com.example.doctoranywhere.model.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidRequestException extends ResponseStatusException
{
    public InvalidRequestException(String message)
    {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
