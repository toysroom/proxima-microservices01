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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.proximagroup.accounts.constants.CustomerConstants;
import eu.proximagroup.accounts.dto.CustomerDetailsDto;
import eu.proximagroup.accounts.dto.ResponseErrorDto;
import eu.proximagroup.accounts.dto.ResponseSuccessDto;
import eu.proximagroup.accounts.entities.Customer;
import eu.proximagroup.accounts.exceptions.InvalidIdFormatIDException;
import eu.proximagroup.accounts.services.CustomerService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private EntityManager entityManager;
	private CustomerService customerService;
	
	public CustomerController(
		CustomerService customerService, 
		EntityManager entityManager
	)
	{
		this.customerService = customerService;
		this.entityManager = entityManager;
	}
	
	@GetMapping
	public ResponseEntity<ResponseSuccessDto<List<Customer>>> index(@RequestParam(defaultValue = "") String sex)
	{
		List<Customer> customers = null;
		if (sex.equals("M") || sex.equals("F"))
		{
			customers = this.customerService.getBySex(sex);						
		}
		else 
		{
			customers = this.customerService.getAll();			
		}

		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseSuccessDto<List<Customer>>(
				HttpStatus.OK,
				CustomerConstants.MESSAGE_200,
				customers
			)
		);
	}
	
	@GetMapping("/search/params")
	public ResponseEntity<?> search(@RequestParam String mobileNumber)
	{
		Customer customer = this.customerService.getByMobileNumber(mobileNumber);
		
		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseSuccessDto<Customer>(
				HttpStatus.OK,
				CustomerConstants.MESSAGE_200,
				customer
			)
		);
	}
	
	
	
	@GetMapping("/{pathId}")
	public ResponseEntity<?> show(@PathVariable String pathId, HttpServletRequest request)
	{
				
		// Validazione che l'ID sia un numero valido
		if (!pathId.matches("\\d+")) {
			throw new InvalidIdFormatIDException(CustomerConstants.ERROR_ID_NUMERIC);	
        }
		
        // Convertiamo l'ID in un Long
        Long id = Long.parseLong(pathId);
		
		Customer customer = this.customerService.getById(id);

		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseSuccessDto<Customer>(
				HttpStatus.OK,
				CustomerConstants.MESSAGE_200,
				customer
			)
		);
		
	}
	
	
	
	
	
	@PostMapping
	public ResponseEntity<?> store(@Valid @RequestBody Customer customer, BindingResult result, HttpServletRequest request)
	{
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
		
        // Verifica se l'email o il telefono esistono già nel database
        Optional<Customer> existingCustomer = customerService.getByEmailOrMobileNumber(customer.getEmail(), customer.getMobileNumber());
        if (existingCustomer.isPresent())
        {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            	new ResponseErrorDto<String>(
            		request.getRequestURI(),
            		request.getMethod(),
            		HttpStatus.BAD_REQUEST,
            		CustomerConstants.MESSAGE_ALREADY_EMAIL_OR_PHONE
            	)
            );
        }
		
		Customer customerInserted = this.customerService.store(customer);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new ResponseSuccessDto<Customer>(
				HttpStatus.CREATED,
				CustomerConstants.MESSAGE_201,
				customerInserted
			)
		);
	}
	
	@PutMapping("/{pathId}")
	public ResponseEntity<?> update(@PathVariable String pathId, @Valid @RequestBody Customer customer, BindingResult result, HttpServletRequest request)
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
		
		this.customerService.getById(id);

				
		this.customerService.update(customer, id);
		
		this.entityManager.clear();
		
		Customer customerUpdated = this.customerService.getById(id);

		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseSuccessDto<Customer>(
				HttpStatus.CREATED,
				CustomerConstants.MESSAGE_201,
				customerUpdated
			)
		);
	}
	
	
	@PatchMapping("/{pathId}")
	public ResponseEntity<?> updateCustomerFields(@PathVariable String pathId, @Valid @RequestBody Map<String, String> updateFields, HttpServletRequest request)
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
        
		List<String> allowedFields = Arrays.asList("firstName", "email");
		
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
		
		Customer customerOptional = this.customerService.getById(id);
		
		
		if (updateFields.containsKey("firstName"))
		{
			customerOptional.setFirstName(updateFields.get("firstName"));
		}
		
		if (updateFields.containsKey("email"))
		{
			customerOptional.setEmail(updateFields.get("email"));
		}
				
		this.customerService.update(customerOptional, id);
		
		this.entityManager.clear();
		
		Customer customerUpdated = this.customerService.getById(id);

		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseSuccessDto<Customer>(
				HttpStatus.OK,
				CustomerConstants.MESSAGE_200,
				customerUpdated
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
        
		this.customerService.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseSuccessDto<String>(
				HttpStatus.NO_CONTENT,
				CustomerConstants.MESSAGE_204,
				null
			)
		);
	}

	
	
	@GetMapping("/details")
	public ResponseEntity<CustomerDetailsDto> details(@RequestParam String mobileNumber)
	{
		// creare un metodo nel service
		CustomerDetailsDto customerDetailsDto = this.customerService.fetchCustomerDetails(mobileNumber);
		
		// restituire una risposta responseEnyity<CustumerDetailsDto>
		return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
	}
}
