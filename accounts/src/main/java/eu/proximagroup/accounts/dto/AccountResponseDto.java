package eu.proximagroup.accounts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountResponseDto {
	private Long id;
	private Long accountNumber;
	private String accountType;
	private String branchAddress;
	private CustomerResponseDto customer;
}
