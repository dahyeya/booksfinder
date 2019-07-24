package mschu.books.http;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;

import mschu.books.error.ResponseParseException;

public class ClientResponse {
	private int status;
	private String body;
	private String contentType = MimeTypeUtils.APPLICATION_JSON_VALUE;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public ClientResponse() {
		
	}
	public ClientResponse(CloseableHttpResponse response) throws ResponseParseException{
		this.parse(response);
	}
	
	public void parse(CloseableHttpResponse response) throws ResponseParseException {
		StatusLine statusLine = response.getStatusLine();

		try {
			this.status = statusLine.getStatusCode();
			this.body = EntityUtils.toString(response.getEntity());
			this.contentType = response.getFirstHeader("Content-Type").getValue();
		} catch (ParseException e) {
			throw new ResponseParseException(e.getMessage());
		} catch (IOException e) {
			throw new ResponseParseException(e.getMessage());
		}
		
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(statusLine.toString());
		strBuffer.append("\n");
		
		for(Header header : response.getAllHeaders()) {
			strBuffer.append(header.getName());
			strBuffer.append(":");
			strBuffer.append(header.getValue());
			strBuffer.append("\n");
		}
		strBuffer.append("\n");
		strBuffer.append(this.body);
		
	}
	
	public ResponseEntity<?> toResponseEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", this.contentType);
		
		return new ResponseEntity<>(this.body, headers, HttpStatus.valueOf(this.status));		
	}
}
