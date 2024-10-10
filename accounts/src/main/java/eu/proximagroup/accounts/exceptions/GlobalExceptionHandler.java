package eu.proximagroup.accounts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import eu.proximagroup.accounts.dto.ResponseErrorDto;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseErrorDto<String>> handleResourceNotFoundException(
		ResourceNotFoundException exception,
		HttpServletRequest request
	)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        	new ResponseErrorDto<String>(
        		request.getRequestURI(),
        		request.getMethod(),
        		HttpStatus.NOT_FOUND,
        		exception.getMessage()
        	)
        );
	}
	
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleTypeMismatch(MethodArgumentTypeMismatchException ex) 
	{
        // Verifica se il parametro che ha causato l'errore è 'id'
        if (ex.getName().equals("id")) {
            // Lancia una custom exception
            throw new InvalidIdFormatIDException("L'ID fornito non è un numero valido.");
        }
    }
	
	@ExceptionHandler(InvalidIdFormatIDException.class)
    public ResponseEntity<ResponseErrorDto<String>> handleInvalidIdFormat(
		InvalidIdFormatIDException exception,
		HttpServletRequest request
    ) 
	{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
			new ResponseErrorDto<String>(
        		request.getRequestURI(),
        		request.getMethod(),
        		HttpStatus.BAD_REQUEST,
        		exception.getMessage()
        	)	
        );
    }
}
