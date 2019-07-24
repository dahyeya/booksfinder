package mschu.books.router;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;

import mschu.books.error.UnauthroizeException;
import mschu.books.http.ErrorResponse;

@ControllerAdvice
public class ErrorAdvisor {
	
	@ExceptionHandler(value = JsonProcessingException.class)
    public ResponseEntity<?> handleJsonProcessingException(JsonProcessingException e) {
        return new ErrorResponse().toResponseEntity(e, "Could not create response message");
    }
		
	@ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {		
        return new ErrorResponse().toResponseEntity(e, "Duplicate key entered");
    }
	
	@ExceptionHandler(value = UnauthroizeException.class)
    public ResponseEntity<?> handleUnauthroizeException(UnauthroizeException e) {		
        return new ErrorResponse().toResponseEntity(e, "Unauthroized");
    }
	
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {		
        return new ErrorResponse().toResponseEntity(e, "Unsupported request method");
    }
}

