package eu.proximagroup.accounts.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ResponseErrorDto<T> {
	private String apiPath;
	private HttpStatus errorCode;
	private T errorMessage;
	private LocalDateTime errorTime;
}
