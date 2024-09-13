package eu.proximagroup.accounts.mappers;

import eu.proximagroup.accounts.dto.CustomerResponseDto;
import eu.proximagroup.accounts.entities.Customer;

public class CustomerMapper {

	public static CustomerResponseDto toResponseDto(Customer customer)
	{
		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		customerResponseDto.setId(customer.getId());
		customerResponseDto.setFirstName(customer.getFirstName());
		customerResponseDto.setLastName(customer.getLastName());
		customerResponseDto.setEmail(customer.getEmail());
		customerResponseDto.setMobileNumber(customer.getMobileNumber());
		return customerResponseDto;
	}
	
	
	public static String toResponseDtoLight(Customer customer)
	{
		return customer.getFirstName() + " " + customer.getLastName();
	}
}
