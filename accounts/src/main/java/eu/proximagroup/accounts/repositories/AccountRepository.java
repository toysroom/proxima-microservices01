package eu.proximagroup.accounts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.proximagroup.accounts.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
