package eu.proximagroup.cards.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.proximagroup.cards.models.Card;

public interface CardRepository extends JpaRepository<Card, Long>{

}
