package eu.proximagroup.accounts.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import eu.proximagroup.accounts.constants.CustomerConstants;
import eu.proximagroup.accounts.dto.AccountCustomerRequestDto;
import eu.proximagroup.accounts.dto.AccountRequestDto;
import eu.proximagroup.accounts.dto.AccountResponseDto;
import eu.proximagroup.accounts.dto.ResponseErrorDto;
import eu.proximagroup.accounts.entities.Account;
import eu.proximagroup.accounts.entities.Customer;
import eu.proximagroup.accounts.mappers.AccountMapper;
import eu.proximagroup.accounts.mappers.CustomerMapper;
import eu.proximagroup.accounts.services.AccountService;
import eu.proximagroup.accounts.services.CustomerService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private EntityManager entityManager;
	private AccountService accountService;
	private CustomerService customerService;
	
	public AccountController(
		AccountService accountService, 
		CustomerService customerService, 
		EntityManager entityManager
	)
	{
		this.accountService = accountService;
		this.entityManager = entityManager;
		this.customerService = customerService;
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
	public ResponseEntity<?> store(@Valid @RequestBody AccountRequestDto accountRequestDto, BindingResult result, HttpServletRequest request)
	{
		
		// validazione sintattica
		if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            result.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));            
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            	new ResponseErrorDto<List<String>>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.BAD_REQUEST,
            		errorMessages
            	)
            );
        }
		
		// verifico se esiste customer
		Optional<Customer> optionalCustomer = this.customerService.getById(accountRequestDto.getCustomerId());
		if (optionalCustomer.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ResponseErrorDto<String>(
					request.getRequestURI(), 
					request.getMethod(), 
					HttpStatus.NOT_FOUND, 
					CustomerConstants.MESSAGE_404
				)
			);
		}
		
		
		Account account = AccountMapper.toEntity(accountRequestDto, optionalCustomer.get());
		
		Account accountInserted = this.accountService.store(account);
		
		AccountResponseDto accountResponseDto = AccountMapper.toResponseDto(accountInserted);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(accountResponseDto);
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
		
		this.accountService.update(account, id);
		
		this.entityManager.clear();
		
		Optional<Account> accountUpdated = this.accountService.getById(id);

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
	
	
	@PostMapping("/create")
	public ResponseEntity<?> store(@Valid @RequestBody AccountCustomerRequestDto accountCustomerRequestDto, BindingResult result, HttpServletRequest request)
	{
		
		// validazione sintattica
		if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            result.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));            
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            	new ResponseErrorDto<List<String>>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.BAD_REQUEST,
            		errorMessages
            	)
            );
        }
		
		
		Customer customer = CustomerMapper.toEntity(accountCustomerRequestDto);
		Customer customerInserted = this.customerService.store(customer);
		
		Account account = AccountMapper.toEntity(accountCustomerRequestDto, customerInserted);
		
		Account accountInserted = this.accountService.store(account);
		
		AccountResponseDto accountResponseDto = AccountMapper.toResponseDto(accountInserted);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(accountResponseDto);

	}
}
