package eu.proximagroup.loans.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import eu.proximagroup.loans.dtos.ResponseErrorDto;
import eu.proximagroup.loans.dtos.ResponseSuccessDto;
import eu.proximagroup.loans.models.Loan;
import eu.proximagroup.loans.services.LoanService;
import eu.proximagroup.loans.utilities.GeneralTools;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
	
	@Autowired
	private LoanService loanService;
	
	private EntityManager entityManager;
	
	@GetMapping
	public ResponseEntity<ResponseSuccessDto<List<Loan>>> index() {
		List<Loan> loans=this.loanService.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseSuccessDto<List<Loan>>(
					HttpStatus.OK,
					"IMMETTERE MESSAGGIO CONTANT",
					loans
					)
				);
	}
	
	@PostMapping
	public ResponseEntity<?> store(@Valid @RequestBody Loan loan,BindingResult result, HttpServletRequest request) {
		if(result.hasErrors()) {
			List<String> errorMessages=new ArrayList<String>();
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
		
		this.loanService.getById(loan.getId());
		
		Loan loanSaved=this.loanService.store(loan);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(
				new ResponseSuccessDto<Loan>(
						HttpStatus.CREATED, 
						"INSERIRE UNA COSTANTE", 
						loanSaved
					)
				);
	}
	
	
	public ResponseEntity<?> update(@PathVariable String pathId,@Valid @RequestBody Loan loan,BindingResult result, HttpServletRequest request) {
		GeneralTools.checkId(pathId, request);
		if(result.hasErrors()) {
			List<String> errorMessages=new ArrayList<String>();
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
		this.loanService.update(loan, Long.parseLong(pathId));
		this.entityManager.clear();
		Loan loanUpdated=this.loanService.getById(Long.parseLong(pathId));
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseSuccessDto<Loan>(
						HttpStatus.CREATED,
						"INSERIRE COSTANTE",
						loanUpdated
					)
				);
	}
	
	@DeleteMapping("/{pathId}")
	public ResponseEntity<?> destroy(@PathVariable String pathId, HttpServletRequest request) {
		GeneralTools.checkId(pathId, request);
		Loan loanDeleted=this.loanService.getById(Long.parseLong(pathId));
		this.loanService.deleteById(Long.parseLong(pathId));
		return ResponseEntity.status(HttpStatus.OK).body(
			new ResponseSuccessDto<Loan>(
					HttpStatus.CREATED,
					"INSERIRE COSTANTE",
					loanDeleted
					)
				);
	}
}
