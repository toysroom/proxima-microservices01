package eu.proximagroup.cards.mappers;

import eu.proximagroup.cards.dtos.CardRequestDto;
import eu.proximagroup.cards.dtos.CardResponseDto;
import eu.proximagroup.cards.models.Card;

public class CardMapper {

	public static Card toEntity(CardRequestDto request) {
		Card card=new Card();
		card.setMobileNumber(request.getMobileNumber());
		card.setCardNumber(request.getCardNumber());
		card.setCardType(request.getCardType());
		card.setTotalLimit(request.getTotalLimit());
		card.setAmountPaid(request.getAmountPaid());
		card.setAvailableAmount(request.getAvailableAmount());
		return card;
	}
	
	public static CardResponseDto toResponseDto(Card card) {
		CardResponseDto cardResponseDto=new CardResponseDto();
		cardResponseDto.setId(card.getId());
		cardResponseDto.setMobileNumber(card.getMobileNumber());
		cardResponseDto.setCardNumber(card.getCardNumber());
		cardResponseDto.setCardType(card.getCardType());
		cardResponseDto.setTotalLimit(card.getTotalLimit());
		cardResponseDto.setAmountPaid(card.getAmountPaid());
		cardResponseDto.setAvailableAmount(card.getAvailableAmount());
		return cardResponseDto;
	}
}
