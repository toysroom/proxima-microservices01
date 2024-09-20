package eu.proximagroup.cards.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eu.proximagroup.cards.constants.CardConstants;
import eu.proximagroup.cards.dtos.ResponseErrorDto;
import jakarta.servlet.http.HttpServletRequest;

public class GeneralTools {
	
	public static ResponseEntity<ResponseErrorDto<String>> checkId(String pathId,HttpServletRequest request) {
		if (!pathId.matches("\\d+")) {
            return ResponseEntity.badRequest().body(
        		new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.BAD_REQUEST,
            		CardConstants.ERROR_ID_NUMERIC
            	)	
            );
        }
		return null;
	}
}
