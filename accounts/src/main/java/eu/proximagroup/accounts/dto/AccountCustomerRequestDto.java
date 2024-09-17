package eu.proximagroup.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountCustomerRequestDto {

	@NotEmpty(message = "account_number is required")
	private Long accountNumber;
	
    @NotEmpty(message = "account_type is required")
    private String accountType;
	
    @NotEmpty(message = "branch_address is required")
    private String branchAddress;
	
    @NotEmpty(message = "first_name is required")
	private String firstName;

    @NotEmpty(message = "last_name is required")
	private String lastName;

    @NotEmpty(message = "email is required")
	@Email(message = "email is not correct")
	private String email;

    @NotEmpty(message = "mobile_number is required")
	private String mobileNumber;

}
