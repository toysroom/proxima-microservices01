package eu.proximagroup.accounts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountResponseDto {
	private Long id;
	private String accountNumber;
	private String accountType;
	private String branchAddress;
	private CustomerResponseDto customer;
}
