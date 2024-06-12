package springbook.learningtest.spring.web.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "서비스 일시 중시")
public class NotInServiceException extends RuntimeException {
    public NotInServiceException(String message) {
        super(message);
    }

    public NotInServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
