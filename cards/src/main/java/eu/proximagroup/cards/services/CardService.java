package eu.proximagroup.cards.services;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.proximagroup.cards.exceptions.ResourceNotFoundException;
import eu.proximagroup.cards.models.Card;
import eu.proximagroup.cards.repositories.CardRepository;

@Service
public class CardService {

	private CardRepository cardRepository;
	
	public CardService(CardRepository cardRepository){
		this.cardRepository = cardRepository;
	}
	
	public List<Card> getAll(){
		return this.cardRepository.findAll();
	}
	
	public Card getById(Long id) {
		isPresent(id);
		Card card=this.cardRepository.findById(id).get();
		return card;
	}
	
	public Card store(Card card) {
		return this.cardRepository.save(card);
	}
	
	public Card update(Card card,Long id) {
		card.setId(id);
		return this.cardRepository.save(card);
	}
	
	public void deleteById(Long id) {
		isPresent(id);
		this.cardRepository.deleteById(id);
	}
	
	public void isPresent(Long id) {
		this.cardRepository.findById(id)
			.orElseThrow( () -> new ResourceNotFoundException("Card", "id", id.toString()));
	}
}
