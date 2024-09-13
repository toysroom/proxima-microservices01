package eu.proximagroup.accounts.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.proximagroup.accounts.entities.Account;
import eu.proximagroup.accounts.services.AccountService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private AccountService accountService;
	
	public AccountController(AccountService accountService)
	{
		this.accountService = accountService;
	}
	
	@GetMapping
	public ResponseEntity<List<Account>> index()
	{
		List<Account> accounts = this.accountService.getAll();
		return ResponseEntity.ok(accounts);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable Long id)
	{
		Optional<Account> account = this.accountService.getById(id);
		if (account.isEmpty())
		{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
		}
		
		return ResponseEntity.ok(account.get());
	}
	
	@PostMapping
	public ResponseEntity<?> store(@Valid @RequestBody Account account, BindingResult result)
	{
		if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            result.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
		
		Account accountInserted = this.accountService.store(account);
		return ResponseEntity.status(HttpStatus.CREATED).body(accountInserted);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Account account, BindingResult result)
	{
		Optional<Account> accountOptional = this.accountService.getById(id);
		if (accountOptional.isEmpty())
		{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
		}
		
		if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            result.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
		
		Account accountUpdated = this.accountService.update(account);
		return ResponseEntity.status(HttpStatus.OK).body(accountUpdated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> destroy(@PathVariable Long id)
	{
		Optional<Account> accountOptional = this.accountService.getById(id);
		if (accountOptional.isEmpty())
		{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
		}

		this.accountService.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
