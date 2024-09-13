package eu.proximagroup.accounts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.proximagroup.accounts.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
