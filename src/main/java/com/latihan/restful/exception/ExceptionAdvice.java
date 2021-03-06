package com.latihan.restful.exception;

import com.latihan.restful.model.response.WebErrorResponse;
import com.latihan.restful.model.response.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors())
        {
            errors.add(error.getField() + " : " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors())
        {
            errors.add(error.getObjectName() +" : "+ error.getDefaultMessage());
        }

        WebErrorResponse webErrorResponse = WebErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .errors(errors)
                .build();

        return handleExceptionInternal(ex, webErrorResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

}
