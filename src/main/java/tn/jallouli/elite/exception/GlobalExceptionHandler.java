package tn.jallouli.elite.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handled generic unpredicted errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGlobalException(Exception ex, HttpServletRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handles validation fails (e.g. from @Valid in RequestBody)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                errorMsg,
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Example: custom exception for purely business rule fails
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Example: custom exception when finding entities by ID fails
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Handles Spring Security @PreAuthorize Failures
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                "You do not have permission to access this resource",
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    // Handles Authentication Failures (e.g., Bad Credentials)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                "Password or email wrong !",
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
