package eu.proximagroup.cards.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.proximagroup.cards.models.Card;

public interface CardRepository extends JpaRepository<Card, Long>{

	ArrayList<Card> findByMobileNumber(String mobileNumber);
}
