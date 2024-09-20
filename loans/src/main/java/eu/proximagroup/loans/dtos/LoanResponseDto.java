package eu.proximagroup.loans.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoanResponseDto {

	private Long id;
	private String mobileNumber;
	private String loanNumber;
	private String loanType;
	private int totalLoan;
	private int amountPaid;
	private int outstandingAmount;
}
