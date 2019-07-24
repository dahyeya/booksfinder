package mschu.books.error;

public class UnauthroizeException extends Exception{
	private static final long serialVersionUID = -5514689915507095031L;
	public UnauthroizeException() {
		super();
	}
	@Override
	public String getMessage() {
		return "Service Error : An unauthorized user request has occurred";
	}
}
