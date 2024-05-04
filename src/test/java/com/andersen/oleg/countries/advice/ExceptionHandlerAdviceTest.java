package com.andersen.oleg.countries.advice;

import com.andersen.oleg.countries.exception.CityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ExceptionHandlerAdviceTest {

    private final ExceptionHandlerAdvice advice = new ExceptionHandlerAdvice();

    @Test
    public void whenUserNotFoundThenReturnNotFound() {
        CityNotFoundException ex = new CityNotFoundException(1L);

        ResponseEntity<?> result = advice.handleAllAccessDeniedExceptions(ex);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND + " \"City with id: 1 not found\"", result.getBody());
    }
}