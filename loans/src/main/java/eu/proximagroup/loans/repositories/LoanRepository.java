package eu.proximagroup.loans.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.proximagroup.loans.models.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long>{

}
