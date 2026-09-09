package Interface;

import java.util.List;

import cards.Card;
import cards.CardData;
import planet.PlanetContentCategory;

public interface ICardService {
	
/**
 * Add a new card using the provided category and card details,
 *  along with the selected unique ID within the repository .
 * 
 * Returns the new created Card.
 * 
 * 
* @param category
* @param cardData
* @param id
* 
* @throws IllegalArgumentException if the card already exists or null
* 
* @return card
*/	
Card addCard(PlanetContentCategory category, CardData cardData);

/**
 *Update the data for the specified card within repository
 *
 * @param card
 * @param cardData(new card data)
 * 
 * @throws IllegalArgumentException if the card is null or cardData null
*/	
void updateCardData(Card card, CardData cardData);

/**
 *Remove the specified card from the repository
 *
 * @param card
 * 
 * @throws IllegalArgumentException if the card is null
*/
void removeCard(Card card);

/**
 *Returns a list of all cards belonging to the selected category
 *
 * @param contentCategory
 * 
 * @return List<>
 */
List<Card> getCardsByContentCategory(PlanetContentCategory contentCategory);

/**
 *Returns a list of all cards
 *
 *@return List<>
*/
List<Card> getAllCards();

/**
 *  Returns the card if it exists within the repository.
 *  
 * @param card
 * 
 * @return card 
 */
Card getCard(Card card);

}
