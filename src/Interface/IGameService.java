package Interface;

import java.util.List;

import cards.Card;
import cards.Language;
import game.GameMode;
import game.GameSession;

public interface IGameService {
	
/**
 * Check if the provided answer is correct.	
 * 
 * @param card
 * @param playerAnswer
 * @return the answer as to whether it is correct or not
 */
boolean checkAnswer(Card card, String playerAnswer);

/**
 * Check if the provided answer is correct.
 * 
 * @param card
 * @param playerAnswer
 * @param language
 * @return the answer as to whether it is correct or not
 */
boolean checkAnswer(Card card, String playerAnswer, Language language);

/**
 * Records the response and updates the player's data for 
 * current game session
 * 
 * @param correct
 * @param session
 */

void registerAnswer(boolean correct, GameSession session);

/**
 * Check if the cards have been learned and return a new list 
 * containing the unlearned ones for given game mode.
 * 
 * @param available
 * @param gameMode
 * @return  a new list with the remaining cards
 */
List<Card> selectCards(List<Card> available, GameMode gameMode);
		        
}
