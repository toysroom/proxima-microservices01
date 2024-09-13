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

import eu.proximagroup.accounts.entities.Customer;
import eu.proximagroup.accounts.services.CustomerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private CustomerService customerService;
	
	public CustomerController(CustomerService customerService)
	{
		this.customerService = customerService;
	}
	
	// restituisce la lista di customers
	@GetMapping
	public ResponseEntity<List<Customer>> index()
	{
		List<Customer> customers = this.customerService.getAll();
		return ResponseEntity.ok(customers);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable Long id)
	{
		Optional<Customer> customer = this.customerService.getById(id);
		if (customer.isEmpty())
		{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
		}
		
		return ResponseEntity.ok(customer.get());
	}
	
	@PostMapping
	public ResponseEntity<?> store(@Valid @RequestBody Customer customer, BindingResult result)
	{
		if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            result.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
		
		Customer customerInserted = this.customerService.store(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(customerInserted);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Customer customer, BindingResult result)
	{
		Optional<Customer> customerOptional = this.customerService.getById(id);
		if (customerOptional.isEmpty())
		{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
		}
		
		System.out.println("CreatedAt: " + customerOptional.get().getCreatedAt());
		if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            result.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
		
		Customer customerUpdated = this.customerService.update(customer, id);
		System.out.println("CreatedAt: " + customerUpdated);
		return ResponseEntity.status(HttpStatus.OK).body(customerUpdated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> destroy(@PathVariable Long id)
	{
		Optional<Customer> customerOptional = this.customerService.getById(id);
		if (customerOptional.isEmpty())
		{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
		}

		this.customerService.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
