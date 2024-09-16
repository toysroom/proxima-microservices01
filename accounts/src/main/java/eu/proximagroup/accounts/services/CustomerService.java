package eu.proximagroup.accounts.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import eu.proximagroup.accounts.entities.Customer;
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

	public Optional<Customer> getById(Long id) {
		return this.customerRepository.findById(id);
	}

	public Customer store(Customer customer) {
		return this.customerRepository.save(customer);
	}
	
	public Customer update(Customer customer, Long id) {
		customer.setId(id);
		return this.customerRepository.save(customer);
	}

	public void deleteById(Long id) {
		this.customerRepository.deleteById(id);
	}
	
	public Optional<Customer> getByEmailOrMobileNumber(String email, String mobileNumber)
	{
		return this.customerRepository.findByEmailOrMobileNumber(email, mobileNumber);
	}

}