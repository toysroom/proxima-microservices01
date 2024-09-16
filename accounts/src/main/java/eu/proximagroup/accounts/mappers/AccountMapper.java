package eu.proximagroup.accounts.mappers;

import eu.proximagroup.accounts.dto.AccountCustomerRequestDto;
import eu.proximagroup.accounts.dto.AccountRequestDto;
import eu.proximagroup.accounts.dto.AccountResponseDto;
import eu.proximagroup.accounts.entities.Account;
import eu.proximagroup.accounts.entities.Customer;

public class AccountMapper {

	public static Account toEntity(AccountRequestDto accountRequestDto, Customer customer)
	{
		Account account = new Account();
		account.setAccountNumber(accountRequestDto.getAccountNumber());
		account.setAccountType(accountRequestDto.getAccountType());
		account.setBranchAddress(accountRequestDto.getBranchAddress());
		account.setCustomer(customer);
		return account;
	}
	
	public static Account toEntity(AccountCustomerRequestDto accountCustomerRequestDto, Customer customer)
	{
		Account account = new Account();
		account.setAccountNumber(accountCustomerRequestDto.getAccountNumber());
		account.setAccountType(accountCustomerRequestDto.getAccountType());
		account.setBranchAddress(accountCustomerRequestDto.getBranchAddress());
		account.setCustomer(customer);
		return account;
	}
	
	
	public static AccountResponseDto toResponseDto(Account account)
	{
		AccountResponseDto accountResponseDto = new AccountResponseDto();
		accountResponseDto.setId(account.getId());
		accountResponseDto.setAccountNumber(account.getAccountNumber());
		accountResponseDto.setAccountType(account.getAccountType());
		accountResponseDto.setBranchAddress(account.getBranchAddress());
		
		
		// CustomerResponseDto customerResponseDto = CustomerMapper.toResponseDto(account.getCustomer());
		// accountResponseDto.setCustomer(customerResponseDto);
		
		accountResponseDto.setCustomer(CustomerMapper.toResponseDto(account.getCustomer()));
		return accountResponseDto;
	}
}
