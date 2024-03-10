package edu.java.exception;

import edu.java.model.response.ApiErrorResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handle(IllegalStateException ex) {
        return new ApiErrorResponse(
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.toString(),
            ex.getClass().toGenericString(),
            ex.getMessage(),
            getStackTraceAsStringArray(ex));
    }

    public List<String> getStackTraceAsStringArray(Exception ex) {
        List<String> stackTraceString = new ArrayList<>();

        for (StackTraceElement element : ex.getStackTrace()) {
            stackTraceString.add(element.toString() + "\n");
        }
        return stackTraceString;
    }
}
