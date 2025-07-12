package org.contoso.estatisticas.config.exception.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ApiError {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final Integer status;
    private final String error;

    private String message;
    private Map<String, String> validationErrors;

    public ApiError(HttpStatus status) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this(status);
        this.message = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, Map<String, String> validationErrors) {
        this(status);
        this.validationErrors = validationErrors;
    }
}