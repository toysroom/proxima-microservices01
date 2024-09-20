package eu.proximagroup.loans.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eu.proximagroup.loans.constants.LoanConstants;
import eu.proximagroup.loans.dtos.ResponseErrorDto;
import jakarta.servlet.http.HttpServletRequest;

public class GeneralTools {
	
	public static ResponseEntity<ResponseErrorDto<String>> checkId(String pathId,HttpServletRequest request) {
		if (!pathId.matches("\\d+")) {
            return ResponseEntity.badRequest().body(
        		new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.BAD_REQUEST,
            		LoanConstants.ERROR_ID_NUMERIC
            	)	
            );
        }
		return null;
	}
}
