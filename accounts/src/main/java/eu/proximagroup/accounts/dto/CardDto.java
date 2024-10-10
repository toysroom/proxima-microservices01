package eu.proximagroup.accounts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CardDto {

	private Long id;
	
	private String mobileNumber;
	
	private String cardNumber;
	
	private String cardType;
	
	private int totalLimit;
	
	private int amountPaid;
	
	private int availableAmount;
}
