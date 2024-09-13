package eu.proximagroup.accounts.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import eu.proximagroup.accounts.entities.Account;
import eu.proximagroup.accounts.repositories.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository)
	{
		this.accountRepository = accountRepository;
	}

	public List<Account> getAll() {
		return this.accountRepository.findAll();
	}

	public Optional<Account> getById(Long id) {
		return this.accountRepository.findById(id);
	}

	public Account store(Account account) {
		return this.accountRepository.save(account);
	}
	
	public Account update(Account account, Long id) {
		account.setId(id);
		return this.accountRepository.save(account);
	}

	public void deleteById(Long id) {
		this.accountRepository.deleteById(id);
	}

}