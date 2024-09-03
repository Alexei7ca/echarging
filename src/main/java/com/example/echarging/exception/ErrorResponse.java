package com.example.echarging.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private List<ErrorDetail> errors;

    public ErrorResponse(HttpStatus status, String message) {
        this.errors = List.of(new ErrorDetail(
                String.valueOf(status.value()),
                message
        ));
    }
}
