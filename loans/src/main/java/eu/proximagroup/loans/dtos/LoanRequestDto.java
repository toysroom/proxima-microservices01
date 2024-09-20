package eu.proximagroup.loans.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoanRequestDto {

private String mobileNumber;
	
	@NotEmpty(message = "loanNumber is required")
	private String loanNumber;
	
	@NotEmpty(message = "loanType is required")
	private String loanType;
	
	@NotNull(message = "totalLoan is required")
	private int totalLoan;
	
	@NotNull(message = "amountPaid is required")
	private int amountPaid;
	
	@NotNull(message = "outstandingAmount is required")
	private int outstandingAmount;
}
