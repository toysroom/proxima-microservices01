package eu.proximagroup.cards.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CardRequestDto {

	@NotEmpty(message = "mobileNumber is required")
	private String mobileNumber;
	
	@NotEmpty(message = "cardNumber is required")
	private String cardNumber;
	
	@NotEmpty(message = "cardType is required")
	private String cardType;
	
	@NotNull(message = "totalLimit is required")
	private int totalLimit;
	
	@NotNull(message = "amountPaid is required")
	private int amountPaid;
	
	@NotNull(message = "availableAmount is required")
	private int availableAmount;
}
