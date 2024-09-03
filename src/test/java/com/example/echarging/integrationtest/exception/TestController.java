package com.example.echarging.integrationtest.exception;

import com.example.echarging.exception.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ServiceUnavailableException;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/not-found")
    public void triggerNotFoundException() {
        throw new NotFoundException("Resource not found");
    }

    @GetMapping("/bad-request")
    public void triggerBadRequestException() {
        throw new IllegalArgumentException("Bad request");
    }

    @GetMapping("/conflict")
    public void triggerConflictException() {
        throw new IllegalStateException("Conflict occurred");
    }

    @GetMapping("/unauthorized")
    public void triggerUnauthorizedException() {
        throw new SecurityException("Unauthorized access");
    }

    @GetMapping("/forbidden")
    public void triggerForbiddenException() throws AccessDeniedException {
        throw new AccessDeniedException("Forbidden access");
    }

    @GetMapping("/service-unavailable")
    public void triggerServiceUnavailableException() throws ServiceUnavailableException {
        throw new ServiceUnavailableException("Service unavailable");
    }
}