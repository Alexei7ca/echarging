package com.example.echarging.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.ServiceUnavailableException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException notFoundException) {
        log.error("NotFoundException Thrown ", notFoundException);
        return new ErrorResponse(HttpStatus.NOT_FOUND, notFoundException.getMessage());
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestExceptions(Exception exception) {
        log.error("{} Thrown ", exception.getClass().getSimpleName(), exception);

        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;
            List<ErrorDetail> errorDetails = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(error -> new ErrorDetail(
                            String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            String.format("Field '%s' rejected value [%s]: %s",
                                    error.getField(),
                                    error.getRejectedValue(),
                                    error.getDefaultMessage())))
                    .collect(Collectors.toList());

            return new ErrorResponse(errorDetails);
        } else {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictExceptions(IllegalStateException exception) {
        log.error("{} Thrown ", exception.getClass().getSimpleName(), exception);
        return new ErrorResponse(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUnauthorizedException(SecurityException exception) {
        log.error("{} Thrown ", exception.getClass().getSimpleName(), exception);
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(AccessDeniedException exception) {
        log.error("AccessDeniedException Thrown ", exception);
        return new ErrorResponse(HttpStatus.FORBIDDEN, "Forbidden access");
    }

    @ExceptionHandler({ServiceUnavailableException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleServiceUnavailableException(ServiceUnavailableException exception) {
        log.error("ServiceUnavailableException Thrown ", exception);
        return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable");
    }

    private ErrorResponse logAndCreateErrorResponse(Exception exception, HttpStatus status) {
        log.error("{} Thrown ", exception.getClass().getSimpleName(), exception);
        return new ErrorResponse(
                status,
                exception.getMessage()
        );
    }
}