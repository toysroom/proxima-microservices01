package eu.proximagroup.accounts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import eu.proximagroup.accounts.dto.ResponseErrorDto;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
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
	
}
