package pl.edu.pw.elka.paprykaisalami.geeruh.spring.errors;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static pl.edu.pw.elka.paprykaisalami.geeruh.spring.errors.ApiError.internalServerError;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException() {
        val error = ApiError.builder()
                .code(ErrorCodes.UNAUTHORIZED)
                .message("Authentication failed.")
                .build();

        return ApiErrors.of(error)
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
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(
            @NotNull MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatus status,
            @NotNull WebRequest request
    ) {
        val errors = ex.getBindingResult().getAllErrors().stream()
                .map(GlobalExceptionHandler::fromJavaxError)
                .toArray(ApiError[]::new);

        return ApiErrors.of(errors)
                .toResponseEntity(HttpStatus.BAD_REQUEST);
    }

    private static ApiError fromJavaxError(ObjectError error) {
        val builder = ApiError.builder()
                .code(ErrorCodes.VALIDATION_ERROR)
                .message(error.getDefaultMessage());

        if (error instanceof FieldError fieldError) {
            builder.path(fieldError.getField());
        }

        return builder.build();
    }

    /**
     * @return If the error happened in an api package then VALIDATION_ERROR.
     * Otherwise, INTERNAL_ERROR.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected @NotNull ResponseEntity<Object> handleConstraintViolated(
            @NotNull ConstraintViolationException ex
    ) {
        var lastCalledGeeruhClass = Arrays.stream(ex.getStackTrace())
                .map(StackTraceElement::getClassName)
                .filter(className -> className.startsWith(GEERUH_PACKAGE))
                .findFirst();

        var lastCallWasInApiPackage = lastCalledGeeruhClass
                .map(API_PACKAGE_PATTERN::matcher)
                .filter(Matcher::find)
                .isPresent();

        ApiError error;

        if (lastCallWasInApiPackage) {
            error = ApiError.builder()
                    .code(ErrorCodes.VALIDATION_ERROR)
                    .message(ex.getMessage())
                    .build();
        } else {
            error = internalServerError();
        }

        return ApiErrors.of(error)
                .toResponseEntity(HttpStatus.BAD_REQUEST);
    }

    private static final String GEERUH_PACKAGE = "pl.edu.pw.elka.paprykaisalami.geeruh";
    private static final Pattern API_PACKAGE_PATTERN = Pattern.compile("\\.api\\.");

    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMessageNotReadable(
            @NotNull HttpMessageNotReadableException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatus status,
            @NotNull WebRequest request
    ) {
        val cause = ex.getRootCause();
        if (cause instanceof InvalidFormatException invalidFormatException) {
            val error = ApiError.builder()
                    .code(ErrorCodes.VALIDATION_ERROR)
                    .message(invalidFormatException.getOriginalMessage())
                    .path(formatJacksonPath(invalidFormatException.getPath()))
                    .build();

            return ApiErrors.of(error)
                    .toResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    private String formatJacksonPath(List<Reference> path) {
        return path.stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleMissingPathVariable(
            @NotNull MissingPathVariableException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatus status,
            @NotNull WebRequest request
    ) {
        val error = ApiError.builder()
                .code(ErrorCodes.VALIDATION_ERROR)
                .message(ex.getMessage())
                .path(ex.getVariableName())
                .build();

        return ApiErrors.of(error)
                .toResponseEntity(status);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            @NotNull HttpRequestMethodNotSupportedException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatus status,
            @NotNull WebRequest request
    ) {
        val error = ApiError.builder()
                .code(ErrorCodes.METHOD_NOT_SUPPORTED)
                .message(formatMethodNotSupported(ex))
                .build();

        return ApiErrors.of(error)
                .toResponseEntity(status);
    }

    private String formatMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return "Request method '%s' not supported. Supported methods: %s".formatted(
                ex.getMethod(),
                ex.getSupportedHttpMethods().stream()
                        .map(HttpMethod::toString)
                        .map("'%s'"::formatted)
                        .collect(Collectors.joining(", "))
        );
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleExceptionInternal(
            @NotNull Exception ex,
            Object body,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatus status,
            @NotNull WebRequest request
    ) {
        log.error("Internal Server Error!", ex);

        return ApiErrors.of(internalServerError())
                .toResponseEntity(status);
    }

    @ExceptionHandler(Exception.class)
    protected @NotNull ResponseEntity<Object> handleException(
            @NotNull Exception ex
    ) {
        log.error("Internal Server Error!", ex);

        return ApiErrors.of(internalServerError())
                .toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
