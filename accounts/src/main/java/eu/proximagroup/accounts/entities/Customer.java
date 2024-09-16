package eu.proximagroup.accounts.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "first_name")
    @NotEmpty(message = "first_name is required")
	private String firstName;

	@Column(name = "last_name")
    @NotEmpty(message = "last_name is required")
	private String lastName;

	@Column(name = "email", unique = true)
    @NotEmpty(message = "email is required")
	@Email(message = "email is not correct")
	private String email;

	@Column(name = "mobile_number", unique = true)
    @NotEmpty(message = "mobile_number is required")
	private String mobileNumber;
	
}
