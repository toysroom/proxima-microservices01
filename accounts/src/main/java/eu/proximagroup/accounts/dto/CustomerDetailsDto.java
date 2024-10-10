package eu.proximagroup.accounts.dto;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerDetailsDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	
	private ArrayList<AccountDto> accountsDto;
	private ArrayList<CardDto> cardsDto;
	private ArrayList<LoanDto> loansDto;
}
