package eu.proximagroup.loans.dtos;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseSuccessDto<T> {

	private HttpStatus code;
	private String message;
	private T data;
	private LocalDateTime time;
	
	public ResponseSuccessDto(HttpStatus code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
		this.time=LocalDateTime.now();
	}
}
