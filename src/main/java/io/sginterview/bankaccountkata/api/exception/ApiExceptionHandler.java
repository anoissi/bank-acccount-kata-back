package io.sginterview.bankaccountkata.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.UUID;

@ControllerAdvice
public class ApiExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Handles bad request exceptions, such as `HttpRequestMethodNotSupportedException`, `MethodArgumentTypeMismatchException`, and `IllegalArgumentException`.
     *
     * @param ex The exception to handle.
     * @return A ResponseEntity with a 400 Bad Request status code and an ErrorResponse body.
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception ex) {
        String errorId = UUID.randomUUID().toString();
        logger.error("errorId  <{}> ", errorId, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorId, ex.getMessage() + ", if the problem persist please contact the api team and communicate the error ID"));
    }

    /**
     * Handles all other exceptions.
     *
     * @param ex The exception to handle.
     * @return A ResponseEntity with a 500 Internal Server Error status code and an ErrorResponse body.
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        String errorId = UUID.randomUUID().toString();
        logger.error("errorId  <{}> ", errorId, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(errorId, "Internal error occurred, please contact the api team and communicate the error ID"));
    }
}
