package mschu.books.error;

public class ResponseParseException extends Exception {
	private static final long serialVersionUID = -1577935736509940917L;
	private String message;
	public ResponseParseException() {
		super();
	}
	@Override
	public String getMessage() {
		return "Service Error : Failed to convert response message.\n" + message;
	}
	public ResponseParseException(String message) {
		super();
		this.message = message;
	}
}
