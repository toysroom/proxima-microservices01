package eu.proximagroup.accounts.mappers;

import eu.proximagroup.accounts.dto.AccountCustomerRequestDto;
import eu.proximagroup.accounts.dto.CustomerResponseDto;
import eu.proximagroup.accounts.entities.Customer;

public class CustomerMapper {

	public static Customer toEntity(AccountCustomerRequestDto accountCustomerRequestDto)
	{
		Customer customer = new Customer();
		customer.setFirstName(accountCustomerRequestDto.getFirstName());
		customer.setLastName(accountCustomerRequestDto.getLastName());
		customer.setEmail(accountCustomerRequestDto.getEmail());
		customer.setMobileNumber(accountCustomerRequestDto.getMobileNumber());
		return customer;
	}
	
	
	
	public static CustomerResponseDto toResponseDto(Customer customer)
	{
		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		customerResponseDto.setId(customer.getId());
		customerResponseDto.setFirstName(customer.getFirstName());
		customerResponseDto.setLastName(customer.getLastName());
		customerResponseDto.setMobileNumber(customer.getMobileNumber());
		return customerResponseDto;
	}
	
	
	public static String toResponseDtoLight(Customer customer)
	{
		return customer.getFirstName() + " " + customer.getLastName();
	}
}
