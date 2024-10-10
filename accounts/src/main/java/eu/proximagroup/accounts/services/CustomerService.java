package eu.proximagroup.accounts.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import eu.proximagroup.accounts.entities.Customer;
import eu.proximagroup.accounts.exceptions.ResourceNotFoundException;
import eu.proximagroup.accounts.repositories.CustomerRepository;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository)
	{
		this.customerRepository = customerRepository;
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

	public Optional<Customer> getByMobileNumber(String mobileNumber)
	{
		return this.customerRepository.findByMobileNumber(mobileNumber);
	}
	
}