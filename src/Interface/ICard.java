package Interface;

import cards.CardData;
import planet.PlanetContentCategory;

public interface ICard {

/**
* Add the card data to the card
* 
* @param cardData
*/
void addCardData(CardData cardData);

/**
 * Returns data of the card
 * 
 * @return cardData
 */
CardData getCardData();

/**
* Returns the category to which the card belongs
* 
* @return PlanetContentCategory 
*/
PlanetContentCategory getCategory() ;

/**
* Returns the unique card ID
* 
* @return id
*/
long getCardId();
	
}