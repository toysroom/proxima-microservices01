package eu.proximagroup.accounts.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(name="account_number")
    @NotEmpty(message = "account_number is required")
    private String accountNumber;

    @Column(name="account_type")
    @NotEmpty(message = "account_type is required")
    private String accountType;

    @Column(name="branch_address")
    @NotEmpty(message = "branch_address is required")
    private String branchAddress;
}
