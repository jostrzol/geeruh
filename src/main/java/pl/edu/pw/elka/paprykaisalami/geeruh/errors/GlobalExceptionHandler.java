package pl.edu.pw.elka.paprykaisalami.geeruh.errors;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMessageNotReadable(
            @NotNull HttpMessageNotReadableException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatus status,
            @NotNull WebRequest request
    ) {
        val cause = ex.getRootCause();
        if (cause instanceof InvalidFormatException invalidFormatException) {
            val error = Error.builder()
                    .code(ErrorCodes.VALIDATION_ERROR)
                    .message(invalidFormatException.getOriginalMessage())
                    .path(formatPath(invalidFormatException.getPath()))
                    .build();

            return Errors.of(error)
                    .toResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    private String formatPath(List<Reference> path) {
        return path.stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException() {
        val error = Error.builder()
                .code(ErrorCodes.UNAUTHORIZED)
                .message("Authentication failed.")
                .build();

        return Errors.of(error)
                .toResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ErrorsException.class)
    protected @NotNull ResponseEntity<Object> handleErrorsException(
            ErrorsException ex,
            WebRequest request
    ) {
        return ex.toResponseEntity();
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleMissingPathVariable(
            @NotNull MissingPathVariableException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatus status,
            @NotNull WebRequest request) {
        val error = Error.builder()
                .code(ErrorCodes.VALIDATION_ERROR)
                .message(ex.getMessage())
                .path(ex.getVariableName())
                .build();

        return Errors.of(error)
                .toResponseEntity(status);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleExceptionInternal(
            @NotNull Exception ex,
            Object body,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatus status,
            @NotNull WebRequest request) {
        log.error("Internal Server Error!", ex);

        val error = Error.builder()
                .code(ErrorCodes.INTERNAL_ERROR)
                .message("Server internal error")
                .build();

        return Errors.of(error)
                .toResponseEntity(status);
    }

    @ExceptionHandler(Exception.class)
    protected @NotNull ResponseEntity<Object> handleException(
            @NotNull Exception ex
    ) {
        log.error("Internal Server Error!", ex);

        val error = Error.builder()
                .code(ErrorCodes.INTERNAL_ERROR)
                .message("Server internal error")
                .build();

        return Errors.of(error)
                .toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}