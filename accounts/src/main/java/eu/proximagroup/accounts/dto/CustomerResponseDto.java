package eu.proximagroup.accounts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerResponseDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String mobileNumber;
}
