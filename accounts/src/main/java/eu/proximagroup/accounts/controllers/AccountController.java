package eu.proximagroup.accounts.controllers;

import java.util.ArrayList;
import java.util.Arrays;
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

import eu.proximagroup.accounts.constants.AccountConstants;
import eu.proximagroup.accounts.constants.CustomerConstants;
import eu.proximagroup.accounts.dto.AccountCustomerRequestDto;
import eu.proximagroup.accounts.dto.AccountRequestDto;
import eu.proximagroup.accounts.dto.AccountResponseDto;
import eu.proximagroup.accounts.dto.ResponseErrorDto;
import eu.proximagroup.accounts.dto.ResponseSuccessDto;
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
	public ResponseEntity<ResponseSuccessDto<List<Account>>> index()
	{
		List<Account> accounts = this.accountService.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseSuccessDto<List<Account>>(
				HttpStatus.OK,
				CustomerConstants.MESSAGE_200,
				accounts
			)
		);
	}
	
	@GetMapping("/{pathId}")
	public ResponseEntity<?> show(@PathVariable String pathId, HttpServletRequest request)
	{
		// Validazione che l'ID sia un numero valido
		if (!pathId.matches("\\d+")) {
            return ResponseEntity.badRequest().body(
        		new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.BAD_REQUEST,
            		CustomerConstants.ERROR_ID_NUMERIC
            	)	
            );
        }
		
        // Convertiamo l'ID in un Long
        Long id = Long.parseLong(pathId);
		        
		Optional<Account> account = this.accountService.getById(id);
		if (account.isEmpty())
		{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        		new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.NOT_FOUND,
            		AccountConstants.MESSAGE_404
            	)		
            );
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseSuccessDto<Account>(
				HttpStatus.OK,
				CustomerConstants.MESSAGE_200,
				account.get()
			)
		);
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
		Customer optionalCustomer = this.customerService.getById(accountRequestDto.getCustomerId());
		
		
		
		Account account = AccountMapper.toEntity(accountRequestDto, optionalCustomer);
		
		Account accountInserted = this.accountService.store(account);
		
		AccountResponseDto accountResponseDto = AccountMapper.toResponseDto(accountInserted);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new ResponseSuccessDto<AccountResponseDto>(
				HttpStatus.CREATED,
				CustomerConstants.MESSAGE_201,
				accountResponseDto
			)		
		);
	}
	
	@PutMapping("/{pathId}")
	public ResponseEntity<?> update(@PathVariable String pathId, @Valid @RequestBody Account account, BindingResult result, HttpServletRequest request)
	{
		// Validazione che l'ID sia un numero valido
		if (!pathId.matches("\\d+")) {
            return ResponseEntity.badRequest().body(
        		new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.BAD_REQUEST,
            		CustomerConstants.ERROR_ID_NUMERIC
            	)	
            );
        }
		
        // Convertiamo l'ID in un Long
        Long id = Long.parseLong(pathId);
		        
		Optional<Account> accountOptional = this.accountService.getById(id);
		if (accountOptional.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        		new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.NOT_FOUND,
            		AccountConstants.MESSAGE_404
            	)		
            );		
		}
		
		if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            result.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
		
		this.accountService.update(account, id);
		
		this.entityManager.clear();
		
		Optional<Account> accountUpdated = this.accountService.getById(id);

		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseSuccessDto<Account>(
				HttpStatus.CREATED,
				CustomerConstants.MESSAGE_200,
				accountUpdated.get()
			)
		);
	}
	
	
	@PatchMapping("/{pathId}")
	public ResponseEntity<?> updateFields(@PathVariable String pathId, @Valid @RequestBody Map<String, String> updateFields, HttpServletRequest request)
	{
		// Validazione che l'ID sia un numero valido
		if (!pathId.matches("\\d+")) {
            return ResponseEntity.badRequest().body(
        		new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.BAD_REQUEST,
            		CustomerConstants.ERROR_ID_NUMERIC
            	)	
            );
        }
		
        // Convertiamo l'ID in un Long
        Long id = Long.parseLong(pathId);
        
		List<String> allowedFields = Arrays.asList("accountType");
		
		// Verifica che i campi ricevuti siano validi
        for (String field : updateFields.keySet()) {
            if (!allowedFields.contains(field)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            		new ResponseErrorDto<String>(
                		request.getRequestURI(),
                		request.getMethod(),
                		HttpStatus.BAD_REQUEST,
                		"Field " + field + " not allowed"
                	)
                );
            }
        }
		
		Optional<Account> accountOptional = this.accountService.getById(id);
		
		if (accountOptional.isEmpty())
		{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            	new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.NOT_FOUND,
            		AccountConstants.MESSAGE_404
            	)
            );
		}
		
		if (updateFields.containsKey("accountType"))
		{
			accountOptional.get().setAccountType(updateFields.get("accountType"));
		}
		
		
				
		this.accountService.update(accountOptional.get(), id);
		
		this.entityManager.clear();
		
		Optional<Account> accountUpdated = this.accountService.getById(id);

		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseSuccessDto<Account>(
				HttpStatus.OK,
				CustomerConstants.MESSAGE_200,
				accountUpdated.get()
			)
		);
	}
	
	@DeleteMapping("/{pathId}")
	public ResponseEntity<?> destroy(@PathVariable String pathId, HttpServletRequest request)
	{
		// Validazione che l'ID sia un numero valido
		if (!pathId.matches("\\d+")) {
            return ResponseEntity.badRequest().body(
        		new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.BAD_REQUEST,
            		CustomerConstants.ERROR_ID_NUMERIC
            	)	
            );
        }
		
        // Convertiamo l'ID in un Long
        Long id = Long.parseLong(pathId);
		        
		Optional<Account> accountOptional = this.accountService.getById(id);
		if (accountOptional.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        		new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.NOT_FOUND,
            		AccountConstants.MESSAGE_404
            	)		
            );
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
		
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new ResponseSuccessDto<AccountResponseDto>(
				HttpStatus.CREATED,
				CustomerConstants.MESSAGE_201,
				accountResponseDto
			)
		);

	}
}
