package eu.proximagroup.cards.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CardResponseDto {

	private Long id;
	private String mobileNumber;
	private String cardNumber;
	private String cardType;
	private int totalLimit;
	private int amountPaid;
	private int availableAmount;
}
