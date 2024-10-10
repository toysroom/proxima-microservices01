package eu.proximagroup.accounts.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.proximagroup.accounts.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	ArrayList<Account> findByCustomerId(Long customerId);
	
}
