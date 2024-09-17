package eu.proximagroup.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountRequestDto {

	@NotNull(message = "account_number is required")
	private Long accountNumber;
	
    @NotEmpty(message = "account_type is required")
    private String accountType;
	
    @NotEmpty(message = "branch_address is required")
    private String branchAddress;
	
    @NotNull(message = "customer_id is required")
    private Long customerId;
}
