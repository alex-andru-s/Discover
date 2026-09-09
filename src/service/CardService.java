package service;

import java.util.Collections;
import java.util.List;

import Interface.ICardService;
import cards.Card;
import cards.CardData;
import planet.PlanetContentCategory;
import repositories.CardRepository;

public class CardService implements ICardService{
	
private CardRepository repository;

public CardService(CardRepository repository){
	this.repository = repository;
}

@Override
public Card addCard(PlanetContentCategory category, CardData cardData){
	long id = getNextId();
	Card card = new Card(id, category, cardData);
	if(cardExist(card) || card == null)
		throw new IllegalArgumentException("The card exists or is null.");
	repository.CreateCard(card);
	return card;
}

private  boolean cardExist(Card card) {
	
	return repository.findAllCards().stream()
            .anyMatch(iCard ->
            iCard.getCardId() == card.getCardId() ||
            iCard.getCardData().getTranslations().stream()
              .anyMatch(p -> card.getCardData().getTranslations()
               .contains(p)));	
}

@Override
public void updateCardData(Card card, CardData cardData) {
	if(!card.equals(null) || !cardData.equals(null))
	 repository.updateCard(card, cardData);
	else throw new IllegalArgumentException(
            "The card or its details are missing.");
}

@Override
public void removeCard(Card card) {
   if(card.equals(null))
	  throw new IllegalArgumentException(
			                   "The card to be discarded is a blank card.");
	 repository.deleteCard(card);
}

@Override
public List<Card> getCardsByContentCategory(PlanetContentCategory contentCategory) {
	return Collections.unmodifiableList(
			repository.findAllCards()
			.stream()
			.filter(card->card.getCategory()
			.equals(contentCategory))
			.toList());
}

@Override
public List<Card> getAllCards(){
	return repository.findAllCards();
}

@Override
public Card getCard(Card card) {
	return repository.findCard(card);
    }

private long getNextId() {

    long id = 0;
    for(Card card : repository.findAllCards()) {
	  if(card.getCardId() > id) 
		   id = card.getCardId();
	  }
    return id + 1;
   }

}
