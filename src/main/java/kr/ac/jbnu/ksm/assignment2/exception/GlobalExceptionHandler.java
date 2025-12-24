package kr.ac.jbnu.ksm.assignment2.exception;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.jbnu.ksm.assignment2.common.error.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
            IllegalArgumentException e,
            HttpServletRequest request
    ) {
        // 너 로그에 찍힌 패턴: "RESOURCE_NOT_FOUND: book not found"
        if (e.getMessage() != null && e.getMessage().startsWith("RESOURCE_NOT_FOUND")) {
            ApiErrorResponse body = ApiErrorResponse.builder()
                    .timestamp(Instant.now().toString())
                    .path(request.getRequestURI())
                    .status(404)
                    .code("RESOURCE_NOT_FOUND")
                    .message(e.getMessage())
                    .details(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }

        ApiErrorResponse body = ApiErrorResponse.builder()
                .timestamp(Instant.now().toString())
                .path(request.getRequestURI())
                .status(400)
                .code("BAD_REQUEST")
                .message(e.getMessage())
                .details(null)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(
            Exception e,
            HttpServletRequest request
    ) {
        ApiErrorResponse body = ApiErrorResponse.builder()
                .timestamp(Instant.now().toString())
                .path(request.getRequestURI())
                .status(500)
                .code("INTERNAL_SERVER_ERROR")
                .message("Internal server error")
                .details(null)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
