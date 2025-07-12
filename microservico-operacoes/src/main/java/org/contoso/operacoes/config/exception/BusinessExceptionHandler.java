package org.contoso.operacoes.config.exception;

import org.contoso.operacoes.config.exception.response.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Exception Handler que trata erros específicos de negócio.
 * <p>
 */
@ControllerAdvice
public class BusinessExceptionHandler {

    private static final Logger LOGGER_TECNICO = LoggerFactory.getLogger(BusinessExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(BusinessException ex) {
        var httpStatus = ex.getClass().getAnnotation(HttpStatusMapping.class);
        if (httpStatus != null) {
            var apiErro = new ApiError(httpStatus.value(), ex);
            return buildResponseEntity(apiErro, ex);
        }

        throw ex; // If no specific HttpStatusMapping is found, rethrow the exception
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        var apiError = new ApiError(BAD_REQUEST, errors);

        return buildResponseEntity(apiError, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        var apiError = new ApiError(INTERNAL_SERVER_ERROR, ex);
        return buildResponseEntity(apiError, ex);
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError, Exception ex) {
        LOGGER_TECNICO.error("Exception sendo capturada, APIErrorCode: {}, Mensagem: {}, Exception: ", apiError.getError(), apiError.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatusCode.valueOf(apiError.getStatus()))
                .body(apiError);
    }
}
