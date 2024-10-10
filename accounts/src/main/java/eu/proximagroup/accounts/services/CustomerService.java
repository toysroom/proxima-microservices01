package eu.proximagroup.accounts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import eu.proximagroup.accounts.dto.AccountDto;
import eu.proximagroup.accounts.dto.CardDto;
import eu.proximagroup.accounts.dto.CustomerDetailsDto;
import eu.proximagroup.accounts.dto.LoanDto;
import eu.proximagroup.accounts.entities.Account;
import eu.proximagroup.accounts.entities.Customer;
import eu.proximagroup.accounts.exceptions.ResourceNotFoundException;
import eu.proximagroup.accounts.repositories.AccountRepository;
import eu.proximagroup.accounts.repositories.CustomerRepository;
import eu.proximagroup.accounts.services.client.CardFeignClient;
import eu.proximagroup.accounts.services.client.LoanFeignClient;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;
    private CardFeignClient cardFeignClient;
    private LoanFeignClient loanFeignClient;

	public CustomerService(
		CustomerRepository customerRepository,
		AccountRepository accountRepository,
		CardFeignClient cardFeignClient,
		LoanFeignClient loanFeignClient
	)
	{
		this.customerRepository = customerRepository;
		this.accountRepository = accountRepository;
		this.cardFeignClient = cardFeignClient;
		this.loanFeignClient = loanFeignClient;
	}

	public List<Customer> getAll() {
		return this.customerRepository.findAll();
	}
	
	public List<Customer> getBySex(String sex) {
		return this.customerRepository.findBySex(sex);
	}

	public Customer getById(Long id) {
		
		Customer customer = this.customerRepository.findById(id)
				.orElseThrow( () -> new ResourceNotFoundException("Customer", "id", id.toString()) );
		
		return customer;
	}

	public Customer store(Customer customer) {
		return this.customerRepository.save(customer);
	}
	
	public Customer update(Customer customer, Long id) {
		customer.setId(id);
		return this.customerRepository.save(customer);
	}

	public void deleteById(Long id) {
		
		this.customerRepository.findById(id)
				.orElseThrow( () -> new ResourceNotFoundException("Customer", "id", id.toString()) );
		
		this.customerRepository.deleteById(id);
	}
	
	public Optional<Customer> getByEmailOrMobileNumber(String email, String mobileNumber)
	{
		return this.customerRepository.findByEmailOrMobileNumber(email, mobileNumber);
	}

	public Customer getByMobileNumber(String mobileNumber)
	{
		return this.customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow( () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
	}
	
	
	
	public CustomerDetailsDto fetchCustomerDetails(String mobileNumber)
	{
		
		// customer
		Customer customer = this.getByMobileNumber(mobileNumber);
		
		// accounts
		ArrayList<Account> accounts = this.accountRepository.findByCustomerId(customer.getId());
		
		//cards
		ResponseEntity<ArrayList<CardDto>> cardsDto = this.cardFeignClient.fetchCard(mobileNumber);
		
		//loans 
		ResponseEntity<ArrayList<LoanDto>> loansDto = this.loanFeignClient.fetchLoan(mobileNumber);
		
		
		// impacchettare tutto in un CustomerDetailsDto
		
		ArrayList<AccountDto> accountsDto = new ArrayList<AccountDto>();
		for (Account account : accounts) {
			AccountDto accountDto = new AccountDto();
			accountDto.setId(account.getId());
			accountDto.setAccountNumber(account.getAccountNumber());
			accountDto.setAccountType(account.getAccountType());
			accountDto.setBranchAddress(account.getBranchAddress());
			
			accountsDto.add(accountDto);
		}
		
		CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
		customerDetailsDto.setId(customer.getId());
		customerDetailsDto.setFirstName(customer.getFirstName());
		customerDetailsDto.setLastName(customer.getLastName());
		customerDetailsDto.setMobileNumber(customer.getMobileNumber());
		customerDetailsDto.setAccountsDto(accountsDto);
		customerDetailsDto.setCardsDto(cardsDto.getBody());
		customerDetailsDto.setLoansDto(loansDto.getBody());

		return customerDetailsDto;
	}
		
}