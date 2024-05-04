package com.andersen.oleg.countries.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CityNotFoundException extends ResponseStatusException {

    public CityNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, String.format("City with id: %s not found", id));
    }
}
