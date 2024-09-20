package eu.proximagroup.loans.services;

import java.util.List;
import org.springframework.stereotype.Service;
import eu.proximagroup.loans.exceptions.ResourceNotFoundException;
import eu.proximagroup.loans.models.Loan;
import eu.proximagroup.loans.repositories.LoanRepository;

@Service
public class LoanService {

	private LoanRepository loanRepository;
	
	public LoanService(LoanRepository loanRepository) {
		this.loanRepository=loanRepository;
	}
	
	public List<Loan> getAll(){
		return loanRepository.findAll();
	}
	
	public Loan getById(Long id) {
		isPresent(id);
		Loan loan=this.loanRepository.findById(id).get();
		return loan;
	}
	
	public Loan store(Loan loan) {
		return this.loanRepository.save(loan);
	}
	
	public Loan update(Loan loan,Long id) {
		loan.setId(id);
		return this.loanRepository.save(loan);
	}
	
	public void deleteById(Long id) {
		isPresent(id);
		this.loanRepository.deleteById(id);
	}
	
	public void isPresent(Long id) {
		this.loanRepository.findById(id)
			.orElseThrow( () -> new ResourceNotFoundException("Loan", "id", id.toString()));
	}
}
