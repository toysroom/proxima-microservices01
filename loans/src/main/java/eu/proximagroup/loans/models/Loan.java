package eu.proximagroup.loans.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "loans")
public class Loan extends BaseEntity{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String mobileNumber;
	
	@Column
	private String loanNumber;
	
	@Column
	private String loanType;
	
	@Column
	private int totalLoan;
	
	@Column
	private int amountPaid;
	
	@Column
	private int outstandingAmount;
}
