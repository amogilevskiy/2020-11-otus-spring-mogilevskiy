package otus.amogilevskiy.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import otus.amogilevskiy.spring.dto.ErrorResponseDto;
import otus.amogilevskiy.spring.service.common.ResourceAlreadyExistsException;
import otus.amogilevskiy.spring.service.common.ResourceException;
import otus.amogilevskiy.spring.service.common.ResourceNotFoundException;

import java.util.Locale;

@RequiredArgsConstructor
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    private ResponseEntity<Object> handleResourceAlreadyExists(
            ResourceException e, Locale locale) {
        return handleResourceException(e, locale, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<Object> handleResourceNotFound(
            ResourceException e, Locale locale) {
        return handleResourceException(e, locale, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> handleResourceException(
            ResourceException e, Locale locale, HttpStatus status) {
        var code = e.getMessage();
        return convertToResponseEntity(new ErrorResponseDto(code, getLocalizedErrorMessage(code, locale)), status);
    }

    private String getLocalizedErrorMessage(String code, Locale locale) {
        return messageSource.getMessage(String.format("error.%s", code), null, locale);
    }

    private ResponseEntity<Object> convertToResponseEntity(ErrorResponseDto dto, HttpStatus status) {
        return new ResponseEntity<>(dto, status);
    }

}
