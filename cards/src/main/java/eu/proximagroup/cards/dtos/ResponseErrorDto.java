package eu.proximagroup.cards.dtos;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseErrorDto<T> {

	private String apiPath;
	private String errorMethod;
	private HttpStatus errorCode;
	private T errorMessage;
	private LocalDateTime errorTime;
	
	public ResponseErrorDto(String apiPath, String errorMethod, HttpStatus errorCode, T errorMessage){
		this.apiPath = apiPath;
		this.errorMethod = errorMethod;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorTime = LocalDateTime.now();
	}
}
