package eu.proximagroup.cards.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
@Entity
@Table(name = "cards")
public class Card extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String mobileNumber;
	
	@Column
	private String cardNumber;
	
	@Column
	private String cardType;
	
	@Column
	private int totalLimit;
	
	@Column
	private int amountPaid;
	
	@Column
	private int availableAmount;
}
