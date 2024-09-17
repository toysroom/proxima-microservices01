package eu.proximagroup.accounts.exceptions;

@SuppressWarnings("serial")
public class CustomerAlreadyExistsException extends RuntimeException {

	public CustomerAlreadyExistsException(String message) {
        super(message);
    }
	
}
