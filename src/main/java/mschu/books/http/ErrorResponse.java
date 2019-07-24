package mschu.books.http;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import mschu.books.error.UnauthroizeException;

public class ErrorResponse {
	private final static String CONTENT_TYPE = MimeTypeUtils.APPLICATION_JSON_VALUE;
	
	public ResponseEntity<?> toResponseEntity(Exception e, String message) {
		HttpStatus status = getStatus(e);
		HttpHeaders headers = getHeaders();
		String body = createBody(e, message);
		
		return new ResponseEntity<>(body, headers, status);		
	}
	
	private HttpStatus getStatus(Exception e) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		if(e instanceof DataIntegrityViolationException) {
			status = HttpStatus.CONFLICT;
		}else if(e instanceof JsonProcessingException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}else if(e instanceof UnauthroizeException){
			status = HttpStatus.UNAUTHORIZED;
		}else if(e instanceof HttpRequestMethodNotSupportedException){
			status = HttpStatus.METHOD_NOT_ALLOWED;
		}else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return status;
	}
	
	private String createBody(Exception e, String message) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		
		rootNode.put("message", message);
		rootNode.put("detail", e.getMessage());
				
		String data = null;
		try {
			data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return data;
	}
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", CONTENT_TYPE);
		
		return headers;
	}
}
