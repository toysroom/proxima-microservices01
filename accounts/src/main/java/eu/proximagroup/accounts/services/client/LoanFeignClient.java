package eu.proximagroup.accounts.services.client;

import java.util.ArrayList;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eu.proximagroup.accounts.dto.LoanDto;

@FeignClient("loans")
public interface LoanFeignClient {

	@GetMapping("/api/loans/search/params")
	public ResponseEntity<ArrayList<LoanDto>> fetchLoan(@RequestParam String mobileNumber);
}
