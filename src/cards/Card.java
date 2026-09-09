package cards;


import Interface.ICard;
import planet.PlanetContentCategory;


public class Card  implements ICard {

private long id;

private PlanetContentCategory category;

private CardData cardData;

public Card(long id, PlanetContentCategory category, CardData cardData) {
	this.id = id;
	this.category = category;
	this.cardData = cardData; 
}

@Override
public void addCardData(CardData cardData) {
	this.cardData = cardData;
}

@Override
public CardData getCardData() {
	return this.cardData;
}

@Override
public  PlanetContentCategory getCategory() {
    return this.category;
}

@Override
public long getCardId() {
	return this.id;
}

}

