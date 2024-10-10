package eu.proximagroup.accounts.exceptions;

@SuppressWarnings("serial")
public class InvalidIdFormatIDException extends RuntimeException {
	public InvalidIdFormatIDException(String message) {
        super(message);
    }
}
