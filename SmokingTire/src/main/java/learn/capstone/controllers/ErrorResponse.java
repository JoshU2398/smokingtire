package learn.capstone.controllers;

import learn.capstone.domain.Result;
import learn.capstone.domain.ResultType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;


public class ErrorResponse {

    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static <T> ResponseEntity<Object> build(Result<T> result) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(result.getType() == null || result.getType() == ResultType.INVALID){
            status = HttpStatus.BAD_REQUEST;
        }else if(result.getType() == ResultType.NOT_FOUND){
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(result.getMessages(), status);
    }
}
