package mschu.books.error;

public class RequestFailedException extends Exception{
	private static final long serialVersionUID = 1769836019313353789L;
	private String message;
	public RequestFailedException() {
		super();
	}
	@Override
	public String getMessage() {
		return "Service Error : An internal server-to-server communication error occurred.\n" + message;
	}
	public RequestFailedException(String message) {
		super();
		this.message = message;
	}
}
