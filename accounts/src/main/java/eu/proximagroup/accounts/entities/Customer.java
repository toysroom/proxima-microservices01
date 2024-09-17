package eu.proximagroup.accounts.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "first_name")
    @NotEmpty(message = "first_name is required")
	@Size(min=3, message = "first name must greater than {min} characters")
	private String firstName;

	@Column(name = "last_name")
    @NotEmpty(message = "last_name is required")
	@Size(min=3, message = "last_name must greater than {min} characters")
	private String lastName;

	@Column(name = "email", unique = true)
    @NotEmpty(message = "email is required")
	@Email(message = "email is not correct")
	private String email;

	@Column(name = "mobile_number", unique = true)
    @NotEmpty(message = "mobile_number is required")
	@Size(min=12, max=15, message = "mobile_number must between {min} e {max} characters")
	@Pattern(regexp = "^(\\\\+|[0]{2})[0-9]{11,14}$", message = "mobile_number must have internationla prefix")
	private String mobileNumber;
	
	@JsonManagedReference
	@OneToMany(mappedBy="customer")
	private List<Account> accounts;
}
