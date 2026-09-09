package Interface;

import java.util.List;

import cards.Card;
import cards.CardData;

public interface ICardRepository {

/**
 * Returns the card if a card with an ID
 *  similar to the given card's ID exists.
 * 
 * @param card
 * 
 * @return card
*/
Card findCard(Card card);

/**
 * Returns a list of all existing cards.
 * 
 * @return List<> 
 */
List<Card> findAllCards();

/**
 *Create a new card with a unique ID.
 *
 * @param card
*/
void CreateCard(Card card);

/**
 *Delete a card using the card's unique ID.
 *
 * @param card
*/
void deleteCard(Card card);

/**
 *Update the details for the specified card.
 *
 * @param card
 * @param cardData(new card data)
*/
void updateCard(Card card, CardData cardData);

}
